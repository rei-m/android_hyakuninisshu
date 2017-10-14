package me.rei_m.hyakuninisshu.model;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.model.karuta.BottomPhrase;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizResult;
import me.rei_m.hyakuninisshu.domain.model.karuta.Phrase;
import me.rei_m.hyakuninisshu.domain.model.quiz.ToriFuda;
import me.rei_m.hyakuninisshu.domain.model.quiz.YomiFuda;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.util.Unit;

@Singleton
public class KarutaQuizModel {

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    private KarutaQuizIdentifier quizId;

    private YomiFuda yomiFuda;

    private List<ToriFuda> toriFudaList;

    private String currentPosition;

    private boolean existNextQuiz;

    @Inject
    public KarutaQuizModel(@NonNull KarutaRepository karutaRepository,
                           @NonNull KarutaQuizRepository karutaQuizRepository) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
    }

    public KarutaQuizIdentifier getQuizId() {
        return quizId;
    }

    public YomiFuda getYomiFuda() {
        return yomiFuda;
    }

    public List<ToriFuda> getToriFudaList() {
        return toriFudaList;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public boolean existNextQuiz() {
        return existNextQuiz;
    }

    private PublishSubject<Boolean> completeStartEventSubject = PublishSubject.create();
    public Observable<Boolean> completeStartEvent = completeStartEventSubject;

    private PublishSubject<KarutaQuizResult> completeAnswerEventSubject = PublishSubject.create();
    public Observable<KarutaQuizResult> completeAnswerEvent = completeAnswerEventSubject;

    private PublishSubject<Unit> errorSubject = PublishSubject.create();
    public Observable<Unit> error = errorSubject;

    public void start(@NonNull KarutaStyle topPhraseStyle,
                      @NonNull KarutaStyle bottomPhraseStyle) {
        karutaQuizRepository.pop()
                .flatMap(karutaQuiz -> karutaQuizRepository.store(karutaQuiz.start(new Date())).toSingleDefault(karutaQuiz))
                .flatMap(functionSetUpProperty(topPhraseStyle, bottomPhraseStyle))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> completeStartEventSubject.onNext(false), e -> errorSubject.onNext(Unit.INSTANCE));
    }

    public void answer(int choiceNo) {

        if (quizId == null) {
            errorSubject.onNext(Unit.INSTANCE);
            return;
        }

        karutaQuizRepository.resolve(quizId).flatMap(new Function<KarutaQuiz, SingleSource<KarutaQuizResult>>() {
            @Override
            public SingleSource<KarutaQuizResult> apply(KarutaQuiz karutaQuiz) {
                karutaQuiz.verify(choiceNo, new Date());
                return karutaQuizRepository.store(karutaQuiz)
                        .andThen(karutaQuizRepository.existNextQuiz())
                        .map(existNextQuiz -> {
                            KarutaQuizModel.this.existNextQuiz = existNextQuiz;
                            return karutaQuiz.getResult();
                        });
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                result -> completeAnswerEventSubject.onNext(result),
                e -> errorSubject.onNext(Unit.INSTANCE)
        );
    }

    public void restart(@NonNull KarutaQuizIdentifier karutaQuizIdentifier,
                        @NonNull KarutaStyle topPhraseStyle,
                        @NonNull KarutaStyle bottomPhraseStyle) {

        karutaQuizRepository.resolve(karutaQuizIdentifier)
                .flatMap(functionSetUpProperty(topPhraseStyle, bottomPhraseStyle))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(karutaQuiz -> {
                    completeStartEventSubject.onNext(karutaQuiz.getResult() != null);
                    if (karutaQuiz.getResult() != null) {
                        completeAnswerEventSubject.onNext(karutaQuiz.getResult());
                    }
                }, e -> errorSubject.onNext(Unit.INSTANCE));
    }

    private Function<KarutaQuiz, SingleSource<KarutaQuiz>> functionSetUpProperty(@NonNull KarutaStyle topPhraseStyle,
                                                                                 @NonNull KarutaStyle bottomPhraseStyle) {
        return karutaQuiz -> {
            Single<List<Karuta>> choiceSingle = Observable.fromIterable(karutaQuiz.getContents().choiceList)
                    .flatMapSingle(karutaRepository::resolve)
                    .toList();

            Single<Karuta> collectSingle = karutaRepository.resolve(karutaQuiz.getContents().collectId);

            Single<Pair<Integer, Integer>> countSingle = karutaQuizRepository.countQuizByAnswered();

            Single<Boolean> existNextQuizSingle = karutaQuizRepository.existNextQuiz();

            return Single.zip(choiceSingle, collectSingle, countSingle, existNextQuizSingle, (karutaList, karuta, count, existNextQuiz) -> {

                this.quizId = karutaQuiz.identifier();

                this.yomiFuda = (topPhraseStyle == KarutaStyle.KANJI) ?
                        new YomiFuda(karuta.topPhrase().first().kanji(),
                                karuta.topPhrase().second().kanji(),
                                karuta.topPhrase().third().kanji()) :
                        new YomiFuda(karuta.topPhrase().first().kana(),
                                karuta.topPhrase().second().kana(),
                                karuta.topPhrase().third().kana());

                Phrase[] fourthPhrases = new Phrase[karutaList.size()];
                Phrase[] fifthPhrases = new Phrase[karutaList.size()];

                for (int i = 0; i < karutaList.size(); i++) {
                    BottomPhrase bottomPhrase = karutaList.get(i).bottomPhrase();
                    fourthPhrases[i] = bottomPhrase.fourth();
                    fifthPhrases[i] = bottomPhrase.fifth();
                }

                List<ToriFuda> toriFudaList = new ArrayList<>();

                if (bottomPhraseStyle == KarutaStyle.KANJI) {
                    for (int i = 0; i < karutaList.size(); i++) {
                        toriFudaList.add(new ToriFuda(fourthPhrases[i].kanji(), fifthPhrases[i].kanji()));
                    }
                } else {
                    for (int i = 0; i < karutaList.size(); i++) {
                        toriFudaList.add(new ToriFuda(fourthPhrases[i].kana(), fifthPhrases[i].kana()));
                    }
                }

                this.toriFudaList = toriFudaList;

                this.currentPosition = String.valueOf(count.second + 1) + " / " + count.first.toString();

                this.existNextQuiz = existNextQuiz;

                return karutaQuiz;
            });
        };
    }
}
