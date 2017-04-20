package me.rei_m.hyakuninisshu.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.AbstractEntity;
import me.rei_m.hyakuninisshu.domain.karuta.model.ExamResult;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaExam;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.util.ArrayUtil;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaConstant;
import me.rei_m.hyakuninisshu.util.Unit;

public class KarutaExamModel {

    private final PublishSubject<Unit> completeStartEventSubject = PublishSubject.create();
    public final Observable<Unit> completeStartEvent = completeStartEventSubject;

    private final PublishSubject<Long> completeAggregateResultsEventSubject = PublishSubject.create();
    public final Observable<Long> completeAggregateResultsEvent = completeAggregateResultsEventSubject;

    private final PublishSubject<ExamResult> completeGetResultEventSubject = PublishSubject.create();
    public final Observable<ExamResult> completeGetResultEvent = completeGetResultEventSubject;

    private final PublishSubject<Unit> notFoundResultEventSubject = PublishSubject.create();
    public final Observable<Unit> norFoundResultEvent = notFoundResultEventSubject;

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaQuizListFactory karutaQuizListFactory;

    private final KarutaExamRepository karutaExamRepository;

    public KarutaExamModel(@NonNull KarutaRepository karutaRepository,
                           @NonNull KarutaQuizRepository karutaQuizRepository,
                           @NonNull KarutaQuizListFactory karutaQuizListFactory,
                           @NonNull KarutaExamRepository karutaExamRepository) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaQuizListFactory = karutaQuizListFactory;
        this.karutaExamRepository = karutaExamRepository;
    }

    public void start() {
        karutaRepository.asEntityList()
                .flatMap(karutaList -> Observable.fromIterable(karutaList).map(AbstractEntity::getIdentifier).toList())
                .flatMap(karutaQuizListFactory::create)
                .flatMapCompletable(karutaQuizList -> {
                    List<KarutaQuiz> finallyKarutaQuizList = new ArrayList<>();
                    for (int targetIndex : ArrayUtil.generateRandomArray(karutaQuizList.size(), karutaQuizList.size())) {
                        finallyKarutaQuizList.add(karutaQuizList.get(targetIndex));
                    }
                    return karutaQuizRepository.initialize(finallyKarutaQuizList);
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> completeStartEventSubject.onNext(Unit.INSTANCE));
    }
    
    public void aggregateResults() {
        karutaQuizRepository.asEntityList()
                .flatMap(karutaQuizList -> Observable.fromIterable(karutaQuizList).map(KarutaQuiz::getResult).toList())
                .flatMap(karutaQuizResultList -> karutaExamRepository.store(karutaQuizResultList, new Date()))
                .flatMap(karutaExamIdentifier -> {
                    List<KarutaExam> karutaExamList = karutaExamRepository.asEntityList().blockingGet();
                    int currentExamCount = karutaExamList.size();
                    if (KarutaExam.MAX_HISTORY_COUNT < currentExamCount) {
                        for (KarutaExam karutaExam : karutaExamList.subList(KarutaExam.MAX_HISTORY_COUNT - 1, currentExamCount - 1)) {
                            karutaExamRepository.delete(karutaExam.getIdentifier()).subscribe();
                        }
                    }
                    return Single.just(karutaExamIdentifier.getValue());
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completeAggregateResultsEventSubject::onNext);
    }

    public void getResult(KarutaExamIdentifier karutaExamIdentifier) {
        karutaExamRepository.resolve(karutaExamIdentifier)
                .map(this::createExamResult)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completeGetResultEventSubject::onNext);
    }

    public void getRecentlyResult() {
        karutaExamRepository.asEntityList().map(karutaExams -> {
            if (karutaExams.isEmpty()) {
                return new ExamResult(0,
                        0,
                        0,
                        new boolean[0]);
            } else {
                return createExamResult(karutaExams.get(0));
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(examResult -> {
            if (0 < examResult.quizCount) {
                completeGetResultEventSubject.onNext(examResult);
            } else {
                notFoundResultEventSubject.onNext(Unit.INSTANCE);
            }
        });
    }

    private ExamResult createExamResult(KarutaExam karutaExam) {
        final boolean[] karutaQuizResultList = new boolean[KarutaConstant.NUMBER_OF_KARUTA];

        Arrays.fill(karutaQuizResultList, true);

        for (KarutaIdentifier wrongKarutaIdentifier : karutaExam.wrongKarutaIdList) {
            int wrongKarutaIndex = (int) wrongKarutaIdentifier.getValue() - 1;
            karutaQuizResultList[wrongKarutaIndex] = false;
        }

        return new ExamResult(karutaExam.totalQuizCount,
                karutaExam.totalQuizCount - karutaExam.wrongKarutaIdList.size(),
                karutaExam.averageAnswerTime,
                karutaQuizResultList);
    }
}
