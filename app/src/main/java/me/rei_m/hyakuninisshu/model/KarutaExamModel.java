package me.rei_m.hyakuninisshu.model;

import android.support.annotation.NonNull;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.util.Unit;

@Singleton
public class KarutaExamModel {

    private final PublishSubject<Unit> completeStartEventSubject = PublishSubject.create();
    public final Observable<Unit> completeStartEvent = completeStartEventSubject;

    private final PublishSubject<KarutaExamIdentifier> completeAggregateResultsEventSubject = PublishSubject.create();
    public final Observable<KarutaExamIdentifier> completeAggregateResultsEvent = completeAggregateResultsEventSubject;

    private final PublishSubject<KarutaExamResult> completeFetchResultEventSubject = PublishSubject.create();
    public final Observable<KarutaExamResult> completeFetchResultEvent = completeFetchResultEventSubject;

    private final PublishSubject<Unit> notFoundResultEventSubject = PublishSubject.create();
    public final Observable<Unit> norFoundResultEvent = notFoundResultEventSubject;

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaExamRepository karutaExamRepository;

    @Inject
    public KarutaExamModel(@NonNull KarutaRepository karutaRepository,
                           @NonNull KarutaQuizRepository karutaQuizRepository,
                           @NonNull KarutaExamRepository karutaExamRepository) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaExamRepository = karutaExamRepository;
    }

    public void start() {
        Single.zip(karutaRepository.list(), karutaRepository.findIds(), Karutas::createQuizSet)
                .flatMapCompletable(karutaQuizRepository::initialize)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> completeStartEventSubject.onNext(Unit.INSTANCE));
    }

    public void aggregateResults() {
        karutaQuizRepository.list()
                .map(karutaQuizzes -> new KarutaExamResult(karutaQuizzes.resultSummary(), karutaQuizzes.wrongKarutaIds()))
                .flatMap(karutaExamResult -> karutaExamRepository.storeResult(karutaExamResult, new Date()))
                .flatMap(karutaExamIdentifier -> karutaExamRepository.adjustHistory(KarutaExam.MAX_HISTORY_COUNT).andThen(Single.just(karutaExamIdentifier)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completeAggregateResultsEventSubject::onNext);
    }

    public void fetchResult(KarutaExamIdentifier karutaExamIdentifier) {
        karutaExamRepository.findBy(karutaExamIdentifier)
                .map(KarutaExam::result)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completeFetchResultEventSubject::onNext);
    }

    public void fetchRecentResult() {
        karutaExamRepository.list()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karutaExams -> {
                    KarutaExam recentKarutaExam = karutaExams.recent();
                    if (recentKarutaExam == null) {
                        notFoundResultEventSubject.onNext(Unit.INSTANCE);
                    } else {
                        completeFetchResultEventSubject.onNext(recentKarutaExam.result());
                    }
                });
    }
}
