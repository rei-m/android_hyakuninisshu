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
package me.rei_m.hyakuninisshu.feature.quiz.di

import dagger.Module
import dagger.Provides
import me.rei_m.hyakuninisshu.action.quiz.QuizActionCreator
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.Device
import me.rei_m.hyakuninisshu.feature.quiz.ui.QuizStore
import me.rei_m.hyakuninisshu.feature.quiz.ui.QuizViewModel
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@Module
class QuizModule(
    private val quizId: KarutaQuizIdentifier,
    private val kamiNoKuStyle: KarutaStyleFilter,
    private val shimoNoKuStyle: KarutaStyleFilter
) {
    @Provides
    @FragmentScope
    fun provideQuizViewModelFactory(
        @Named("vmCoroutineContext") coroutineContext: CoroutineContext,
        store: QuizStore,
        actionCreator: QuizActionCreator,
        device: Device
    ): QuizViewModel.Factory {
        return QuizViewModel.Factory(
            coroutineContext,
            store,
            actionCreator,
            quizId,
            kamiNoKuStyle,
            shimoNoKuStyle,
            device
        )
    }
}
