package me.rei_m.hyakuninisshu.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizPosition;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.util.Unit;

@Singleton
public class KarutaQuizModel {

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    @Inject
    public KarutaQuizModel(@NonNull KarutaRepository karutaRepository,
                           @NonNull KarutaQuizRepository karutaQuizRepository) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
    }

    private PublishSubject<KarutaQuizContent> completeStartEventSubject = PublishSubject.create();
    public Observable<KarutaQuizContent> completeStartEvent = completeStartEventSubject;

    private PublishSubject<KarutaQuizContent> completeAnswerEventSubject = PublishSubject.create();
    public Observable<KarutaQuizContent> completeAnswerEvent = completeAnswerEventSubject;

    private PublishSubject<Unit> errorSubject = PublishSubject.create();
    public Observable<Unit> error = errorSubject;

    public void start() {
        karutaQuizRepository.pop()
                .flatMap(karutaQuiz -> karutaQuizRepository.store(karutaQuiz.start(new Date())).toSingleDefault(karutaQuiz))
                .flatMap(this::createContent)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> completeStartEventSubject.onNext(v), e -> errorSubject.onNext(Unit.INSTANCE));
    }

    public void answer(@NonNull KarutaQuizIdentifier quizId, int choiceNo) {
        karutaQuizRepository.resolve(quizId)
                .flatMap(karutaQuiz -> karutaQuizRepository.store(karutaQuiz.verify(choiceNo, new Date())).toSingleDefault(karutaQuiz))
                .flatMap(this::createContent)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> completeAnswerEventSubject.onNext(v), e -> errorSubject.onNext(Unit.INSTANCE));
    }

    public void restart(@NonNull KarutaQuizIdentifier quizId) {
        karutaQuizRepository.resolve(quizId)
                .flatMap(this::createContent)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karutaQuiz -> {
                    completeStartEventSubject.onNext(karutaQuiz);
                    if (karutaQuiz.result() != null) {
                        completeAnswerEventSubject.onNext(karutaQuiz);
                    }
                }, e -> errorSubject.onNext(Unit.INSTANCE));
    }

    private Single<KarutaQuizContent> createContent(@NonNull KarutaQuiz karutaQuiz) {
        Single<List<Karuta>> choiceSingle = Observable.fromIterable(karutaQuiz.choiceList())
                .flatMapSingle(karutaRepository::findBy)
                .toList();

        Single<Karuta> collectSingle = karutaRepository.findBy(karutaQuiz.collectId());

        Single<KarutaQuizPosition> countSingle = karutaQuizRepository.countQuizByAnswered();

        Single<Boolean> existNextQuizSingle = karutaQuizRepository.existNextQuiz();

        return Single.zip(choiceSingle, collectSingle, countSingle, existNextQuizSingle, (karutaList, karuta, count, existNextQuiz) ->
                new KarutaQuizContent(karutaQuiz, karuta, karutaList, count, existNextQuiz));
    }
}
