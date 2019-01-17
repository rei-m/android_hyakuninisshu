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
package me.rei_m.hyakuninisshu.presentation.core.di

import dagger.Module
import dagger.Provides
import me.rei_m.hyakuninisshu.action.quiz.QuizActionCreator
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.presentation.core.QuizAnswerViewModel
import me.rei_m.hyakuninisshu.presentation.core.QuizStore
import me.rei_m.hyakuninisshu.presentation.helper.Navigator

@Module
class QuizAnswerFragmentModule(
    private val quizId: KarutaQuizIdentifier
) {
    @Provides
    @ForFragment
    fun provideQuizAnswerViewModelFactory(
        store: QuizStore,
        actionCreator: QuizActionCreator,
        navigator: Navigator
    ): QuizAnswerViewModel.Factory {
        return QuizAnswerViewModel.Factory(
            store,
            actionCreator,
            quizId,
            navigator
        )
    }
}
