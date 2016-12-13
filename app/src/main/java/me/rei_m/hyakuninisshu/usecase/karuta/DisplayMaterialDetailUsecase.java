package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Maybe;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialDetailViewModel;

public interface DisplayMaterialDetailUsecase {
    Maybe<MaterialDetailViewModel> execute(int karutaNo);
}
