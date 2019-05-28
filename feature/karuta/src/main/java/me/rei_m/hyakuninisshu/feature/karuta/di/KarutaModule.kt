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
package me.rei_m.hyakuninisshu.feature.karuta.di

import dagger.Module
import dagger.Provides
import me.rei_m.hyakuninisshu.action.karuta.KarutaActionCreator
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.karuta.ui.KarutaStore
import me.rei_m.hyakuninisshu.feature.karuta.ui.KarutaViewModel
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@Module
class KarutaModule(
    private val karutaId: KarutaIdentifier
) {
    @Provides
    @ActivityScope
    fun provideKarutaViewModelFactory(
        @Named("mainContext") mainContext: CoroutineContext,
        store: KarutaStore,
        actionCreator: KarutaActionCreator
    ): KarutaViewModel.Factory {
        return KarutaViewModel.Factory(
            mainContext,
            store,
            actionCreator,
            karutaId
        )
    }
}
