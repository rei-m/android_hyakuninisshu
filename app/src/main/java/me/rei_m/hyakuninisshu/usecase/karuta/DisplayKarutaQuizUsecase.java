package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;

public interface DisplayKarutaQuizUsecase {
    Observable<QuizViewModel> execute();
}
