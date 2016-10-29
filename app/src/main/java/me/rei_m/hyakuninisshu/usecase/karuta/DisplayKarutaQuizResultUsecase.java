package me.rei_m.hyakuninisshu.usecase.karuta;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizResultViewModel;
import rx.Observable;

public interface DisplayKarutaQuizResultUsecase {
    Observable<QuizResultViewModel> execute();
}
