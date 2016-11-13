package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.databinding.ObservableField;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.QuizState;

public class StateVm {
    public final ObservableField<QuizState> trainingRange = new ObservableField<>();

    public StateVm() {
        this.trainingRange.set(QuizState.UNANSWERED);
    }
}
