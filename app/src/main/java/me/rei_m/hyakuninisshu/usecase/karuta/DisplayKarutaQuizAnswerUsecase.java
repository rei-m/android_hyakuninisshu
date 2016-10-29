package me.rei_m.hyakuninisshu.usecase.karuta;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizAnswerViewModel;
import rx.Observable;

public interface DisplayKarutaQuizAnswerUsecase {
    Observable<QuizAnswerViewModel> execute(String quizId);
}
