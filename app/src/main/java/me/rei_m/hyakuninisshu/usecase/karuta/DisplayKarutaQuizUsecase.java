package me.rei_m.hyakuninisshu.usecase.karuta;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;
import rx.Observable;

public interface DisplayKarutaQuizUsecase {
    Observable<QuizViewModel> execute();
}
