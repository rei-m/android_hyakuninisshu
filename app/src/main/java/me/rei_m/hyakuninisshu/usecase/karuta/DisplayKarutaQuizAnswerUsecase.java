package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Single;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizAnswerViewModel;

public interface DisplayKarutaQuizAnswerUsecase {
    Single<QuizAnswerViewModel> execute(String quizId);
}
