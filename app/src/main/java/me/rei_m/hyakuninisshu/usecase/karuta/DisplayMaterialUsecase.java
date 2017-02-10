package me.rei_m.hyakuninisshu.usecase.karuta;

import android.support.annotation.Nullable;

import io.reactivex.Single;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialViewModel;

public interface DisplayMaterialUsecase {
    Single<MaterialViewModel> execute(@Nullable String color);
}
