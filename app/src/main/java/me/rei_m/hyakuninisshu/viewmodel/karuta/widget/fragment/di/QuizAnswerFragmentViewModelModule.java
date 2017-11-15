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

package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.QuizAnswerFragmentViewModel;

@Module
public class QuizAnswerFragmentViewModelModule {

    private final KarutaIdentifier karutaId;

    private final boolean existNextQuiz;

    public QuizAnswerFragmentViewModelModule(@NonNull KarutaIdentifier karutaId, boolean existNextQuiz) {
        this.karutaId = karutaId;
        this.existNextQuiz = existNextQuiz;
    }

    @Provides
    @ForFragment
    QuizAnswerFragmentViewModel.Factory provideQuizAnswerFragmentViewModelFactory(@NonNull KarutaModel karutaModel) {
        return new QuizAnswerFragmentViewModel.Factory(karutaModel, karutaId, existNextQuiz);
    }
}
