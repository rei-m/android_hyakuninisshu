package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Single;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialDetailViewModel;

public interface DisplayMaterialDetailUsecase {
    Single<MaterialDetailViewModel> execute(int karutaNo);
}
