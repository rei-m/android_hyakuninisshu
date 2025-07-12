/*
 * Copyright (c) 2025. Rei Matsushita
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

package me.rei_m.hyakuninisshu.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import me.rei_m.hyakuninisshu.infrastructure.di.InfrastructureModule
import me.rei_m.hyakuninisshu.state.core.di.StateCoreModule
import javax.inject.Singleton

@Singleton
@Component(modules = [InfrastructureModule::class, StateCoreModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
}
