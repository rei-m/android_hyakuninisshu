/*
 * Copyright (c) 2017. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package me.rei_m.hyakuninisshu.viewmodel.karuta.di;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;
import me.rei_m.hyakuninisshu.viewmodel.karuta.MaterialDetailActivityViewModel;

@Module
public class MaterialDetailActivityViewModelModule {

    private final ColorFilter colorFilter;

    public MaterialDetailActivityViewModelModule(@NonNull ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
    }

    @Provides
    @ForActivity
    MaterialDetailActivityViewModel.Factory provideFactory(@NonNull KarutaModel karutaModel) {
        return new MaterialDetailActivityViewModel.Factory(karutaModel, colorFilter);
    }
}