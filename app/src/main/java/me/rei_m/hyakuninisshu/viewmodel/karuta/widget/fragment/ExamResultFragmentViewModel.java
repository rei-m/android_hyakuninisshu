package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class ExamResultFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableField<String> score = new ObservableField<>();

    public final ObservableFloat averageAnswerTime = new ObservableFloat();

    public final ObservableField<boolean[]> karutaQuizResultList = new ObservableField<>();

    private final PublishSubject<Unit> onClickBackMenuEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickBackMenuEvent = onClickBackMenuEventSubject;

    private final KarutaExamModel karutaExamModel;

    private final Navigator navigator;

    private final AnalyticsManager analyticsManager;

    private KarutaExamIdentifier karutaExamIdentifier;

    public ExamResultFragmentViewModel(@NonNull KarutaExamModel karutaExamModel,
                                       @NonNull Navigator navigator,
                                       @NonNull AnalyticsManager analyticsManager) {
        this.karutaExamModel = karutaExamModel;
        this.navigator = navigator;
        this.analyticsManager = analyticsManager;
    }

    public void onCreate(long karutaExamId) {
        karutaExamIdentifier = new KarutaExamIdentifier(karutaExamId);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaExamModel.completeGetResultEvent.subscribe(examResult -> {
            score.set(examResult.collectCount + "/" + examResult.quizCount);
            averageAnswerTime.set(examResult.averageAnswerTime);
            karutaQuizResultList.set(examResult.resultList);
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.EXAM_RESULT);
        karutaExamModel.getResult(karutaExamIdentifier);
    }

    @SuppressWarnings("unused")
    public void onClickBackMenu(View view) {
        onClickBackMenuEventSubject.onNext(Unit.INSTANCE);
    }

    public void onClickResult(int karutaNo) {
        navigator.navigateToMaterialSingle(karutaNo);
    }
}
