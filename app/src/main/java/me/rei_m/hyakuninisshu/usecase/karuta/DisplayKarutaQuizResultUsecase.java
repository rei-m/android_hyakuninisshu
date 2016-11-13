package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizResultViewModel;

public interface DisplayKarutaQuizResultUsecase {
    Observable<QuizResultViewModel> execute();
}
