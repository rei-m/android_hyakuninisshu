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

package me.rei_m.hyakuninisshu.feature.material.di

import dagger.Subcomponent
import me.rei_m.hyakuninisshu.feature.material.ui.MaterialDetailFragment
import me.rei_m.hyakuninisshu.feature.material.ui.MaterialListFragment

@Subcomponent
interface MaterialComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MaterialComponent
    }

    fun inject(fragment: MaterialListFragment)

    fun inject(fragment: MaterialDetailFragment)

    interface Injector {
        fun materialComponent(): Factory
    }
}
