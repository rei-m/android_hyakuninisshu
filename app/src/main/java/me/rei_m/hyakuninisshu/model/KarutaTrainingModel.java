package me.rei_m.hyakuninisshu.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas;
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzes;
import me.rei_m.hyakuninisshu.domain.model.quiz.TrainingResult;
import me.rei_m.hyakuninisshu.util.Unit;

@Singleton
public class KarutaTrainingModel {

    private final PublishSubject<Unit> completeStartEventSubject = PublishSubject.create();
    public final Observable<Unit> completeStartEvent = completeStartEventSubject;

    private final PublishSubject<Unit> completeRestartEventSubject = PublishSubject.create();
    public final Observable<Unit> completeRestartEvent = completeRestartEventSubject;

    private final PublishSubject<Unit> completeStartForExamEventSubject = PublishSubject.create();
    public final Observable<Unit> completeStartForExamEvent = completeStartForExamEventSubject;

    private final PublishSubject<TrainingResult> completeAggregateResultsEventSubject = PublishSubject.create();
    public final Observable<TrainingResult> completeAggregateResultsEvent = completeAggregateResultsEventSubject;

    private final PublishSubject<Unit> notFoundErrorEventSubject = PublishSubject.create();
    public final Observable<Unit> notFoundErrorEvent = notFoundErrorEventSubject;

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaExamRepository karutaExamRepository;

    @Inject
    public KarutaTrainingModel(@NonNull KarutaRepository karutaRepository,
                               @NonNull KarutaQuizRepository karutaQuizRepository,
                               @NonNull KarutaExamRepository karutaExamRepository) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaExamRepository = karutaExamRepository;
    }

    public void start(int fromKarutaId,
                      int toKarutaId,
                      int kimarijiPosition,
                      @Nullable String color) {

        KarutaIdentifier fromId = new KarutaIdentifier(fromKarutaId);
        KarutaIdentifier toId = new KarutaIdentifier(toKarutaId);

        Single<Karutas> karutasSingle = karutaRepository.findAll();

        Single<KarutaIds> trainingKarutaIdsSingle = (kimarijiPosition == 0) ?
                karutaRepository.findForTraining(fromId, toId, color) :
                karutaRepository.findForTraining(fromId, toId, color, new Kimariji(kimarijiPosition));

        Single.zip(karutasSingle, trainingKarutaIdsSingle, Karutas::createQuizSet)
                .flatMap(karutaQuizList -> karutaQuizRepository.initialize(karutaQuizList).andThen(Single.just(!karutaQuizList.isEmpty())))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(hasQuiz -> {
                    if (hasQuiz) {
                        completeStartEventSubject.onNext(Unit.INSTANCE);
                    } else {
                        notFoundErrorEventSubject.onNext(Unit.INSTANCE);
                    }
                },
                throwable -> notFoundErrorEventSubject.onNext(Unit.INSTANCE)
        );
    }

    public void restartForPractice() {
        Single<Karutas> karutasSingle = karutaRepository.findAll();
        Single<KarutaQuizzes> karutaQuizzesSingle = karutaQuizRepository.list();

        Single.zip(karutasSingle, karutaQuizzesSingle, (karutas, karutaQuizzes) -> karutas.createQuizSet(karutaQuizzes.wrongKarutaIds()))
                .flatMap(karutaQuizList -> karutaQuizRepository.initialize(karutaQuizList).andThen(Single.just(!karutaQuizList.isEmpty())))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(hasQuiz -> {
                    if (hasQuiz) {
                        completeRestartEventSubject.onNext(Unit.INSTANCE);
                    } else {
                        notFoundErrorEventSubject.onNext(Unit.INSTANCE);
                    }
                },
                throwable -> notFoundErrorEventSubject.onNext(Unit.INSTANCE));
    }

    public void startForExam() {
        Single<Karutas> karutasSingle = karutaRepository.findAll();
        Single<KarutaIds> trainingKarutaIdsSingle = karutaExamRepository.list()
                .flatMap(karutaExamList -> Observable.fromIterable(karutaExamList)
                        .reduce(new ArrayList<KarutaIdentifier>(), (karutaIdList, karutaExam) -> {
                            for (KarutaIdentifier wrongKarutaId : karutaExam.wrongKarutaIdList) {
                                if (!karutaIdList.contains(wrongKarutaId)) {
                                    karutaIdList.add(wrongKarutaId);
                                }
                            }
                            return karutaIdList;
                        })
                        .map(KarutaIds::new));

        Single.zip(karutasSingle, trainingKarutaIdsSingle, Karutas::createQuizSet)
                .flatMapCompletable(karutaQuizRepository::initialize)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> completeStartForExamEventSubject.onNext(Unit.INSTANCE));
    }

    public void aggregateResults() {
        karutaQuizRepository.list().map(KarutaQuizzes::resultSummary)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        completeAggregateResultsEventSubject::onNext,
                        throwable -> notFoundErrorEventSubject.onNext(Unit.INSTANCE)
                );
    }
}
