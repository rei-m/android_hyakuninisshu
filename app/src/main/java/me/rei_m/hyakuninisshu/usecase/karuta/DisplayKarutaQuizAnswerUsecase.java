package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizAnswerViewModel;

public interface DisplayKarutaQuizAnswerUsecase {
    Observable<QuizAnswerViewModel> execute(String quizId);
}
