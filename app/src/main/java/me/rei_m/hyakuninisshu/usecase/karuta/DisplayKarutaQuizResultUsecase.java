package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Single;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizResultViewModel;

public interface DisplayKarutaQuizResultUsecase {
    Single<QuizResultViewModel> execute();
}
