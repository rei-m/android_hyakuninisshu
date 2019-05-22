/*
 * Copyright (c) 2019. Rei Matsushita
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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.presentation.materialdetail.di

import dagger.Module
import dagger.Provides
import me.rei_m.hyakuninisshu.action.material.MaterialActionCreator
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.ColorFilter
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import me.rei_m.hyakuninisshu.presentation.materialdetail.MaterialDetailStore
import me.rei_m.hyakuninisshu.presentation.materialdetail.MaterialDetailViewModel

@Module
class MaterialDetailActivityModule(
    private val colorFilter: ColorFilter,
    private val initialPosition: Int
) {
    @Provides
    @ActivityScope
    fun provideMaterialDetailViewModelFactory(
        store: MaterialDetailStore,
        actionCreator: MaterialActionCreator,
        navigator: Navigator
    ): MaterialDetailViewModel.Factory {
        return MaterialDetailViewModel.Factory(
            store,
            actionCreator,
            navigator,
            colorFilter,
            initialPosition
        )
    }
}
