package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizResultUsecase;

public class QuizResultPresenter implements QuizResultContact.Actions {

    private final DisplayKarutaQuizResultUsecase displayKarutaQuizResultUsecase;

    private QuizResultContact.View view;

    public QuizResultPresenter(DisplayKarutaQuizResultUsecase displayKarutaQuizResultUsecase) {
        this.displayKarutaQuizResultUsecase = displayKarutaQuizResultUsecase;
    }

    @Override
    public void onCreate(@NonNull QuizResultContact.View view) {
        this.view = view;
    }

    @Override
    public void onCreateView() {
        displayKarutaQuizResultUsecase.execute().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::initialize);
    }
}