package me.rei_m.hyakuninisshu.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.model.quiz.TrainingResult;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.util.ArrayUtil;
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

    private final KarutaQuizListFactory karutaQuizListFactory;

    private final KarutaExamRepository karutaExamRepository;

    @Inject
    public KarutaTrainingModel(@NonNull KarutaRepository karutaRepository,
                               @NonNull KarutaQuizRepository karutaQuizRepository,
                               @NonNull KarutaQuizListFactory karutaQuizListFactory,
                               @NonNull KarutaExamRepository karutaExamRepository) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaQuizListFactory = karutaQuizListFactory;
        this.karutaExamRepository = karutaExamRepository;
    }

    public void start(int fromKarutaId,
                      int toKarutaId,
                      int kimarijiPosition,
                      @Nullable String color) {

        int quizSize = toKarutaId - fromKarutaId + 1;

        Single<List<Karuta>> karutaListObservable = (kimarijiPosition == 0) ?
                karutaRepository.asEntityList(new KarutaIdentifier(fromKarutaId), new KarutaIdentifier(toKarutaId), color) :
                karutaRepository.asEntityList(new KarutaIdentifier(fromKarutaId), new KarutaIdentifier(toKarutaId), color, kimarijiPosition);

        karutaListObservable.map(karutaList -> {
            List<KarutaIdentifier> correctKarutaIdList = new ArrayList<>();
            int size = (karutaList.size() < quizSize) ? karutaList.size() : quizSize;
            int[] collectKarutaIndexList = ArrayUtil.generateRandomArray(karutaList.size(), size);
            for (int i : collectKarutaIndexList) {
                correctKarutaIdList.add(karutaList.get(i).identifier());
            }
            return correctKarutaIdList;
        }).flatMap(karutaQuizListFactory::create).flatMap(karutaQuizList -> karutaQuizRepository.initialize(karutaQuizList).andThen(Single.just(!karutaQuizList.isEmpty())))
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
        karutaQuizRepository.asEntityList()
                .flatMap(karutaQuizList -> Observable.fromIterable(karutaQuizList)
                        .filter(karutaQuiz -> karutaQuiz.getResult() != null && !karutaQuiz.getResult().isCollect)
                        .map(karutaQuiz -> karutaQuiz.getResult().collectKarutaId)
                        .toList())
                .flatMap(karutaQuizListFactory::create)
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
        karutaExamRepository.asEntityList()
                .flatMap(karutaExamList -> Observable.fromIterable(karutaExamList)
                        .reduce(new ArrayList<KarutaIdentifier>(), (karutaIdList, karutaExam) -> {
                            for (KarutaIdentifier wrongKarutaId : karutaExam.wrongKarutaIdList) {
                                if (!karutaIdList.contains(wrongKarutaId)) {
                                    karutaIdList.add(wrongKarutaId);
                                }
                            }
                            return karutaIdList;
                        }))
                .flatMap(karutaQuizListFactory::create)
                .flatMapCompletable(karutaQuizRepository::initialize)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> completeStartForExamEventSubject.onNext(Unit.INSTANCE));
    }

    public void aggregateResults() {
        karutaQuizRepository.asEntityList().map(karutaQuizList -> {

            final int quizCount = karutaQuizList.size();

            long totalAnswerTimeMillSec = 0;

            int collectCount = 0;

            for (KarutaQuiz karutaQuiz : karutaQuizList) {
                if (karutaQuiz.getResult() == null) {
                    throw new IllegalStateException("Training is not finished.");
                }

                totalAnswerTimeMillSec += karutaQuiz.getResult().answerTime;
                if (karutaQuiz.getResult().isCollect) {
                    collectCount++;
                }
            }

            final float averageAnswerTime = totalAnswerTimeMillSec / (float) quizCount / (float) TimeUnit.SECONDS.toMillis(1);

            final boolean canRestartTraining = collectCount != quizCount;

            return new TrainingResult(quizCount, collectCount, averageAnswerTime, canRestartTraining);
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                completeAggregateResultsEventSubject::onNext,
                throwable -> notFoundErrorEventSubject.onNext(Unit.INSTANCE)
        );
    }
}
