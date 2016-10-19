package me.rei_m.hyakuninisshu.usecase.karuta;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.KarutaQuizViewModel;
import rx.Observable;

public interface DisplayKarutaQuizUsecase {
    Observable<KarutaQuizViewModel> execute();
}
