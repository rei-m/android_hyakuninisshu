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

package me.rei_m.hyakuninisshu.state.core.di

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import me.rei_m.hyakuninisshu.domain.question.service.CreateQuestionListService
import me.rei_m.hyakuninisshu.state.core.ActionDispatcher
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import javax.inject.Singleton

@Module
class StateCoreModule {
    @Provides
    @Singleton
    fun provideActionDispatcher(): Dispatcher = ActionDispatcher(AndroidSchedulers.mainThread())

    @Provides
    fun provideCreateQuestionListService(): CreateQuestionListService = CreateQuestionListService()
}
