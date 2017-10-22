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

package me.rei_m.hyakuninisshu.infrastructure.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaExamRepositoryImpl;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaQuizRepositoryImpl;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaRepositoryImpl;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;

@Module
public class InfrastructureModule {

    private static final String KEY_PREFERENCES = "B84UqpLA7W";

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    OrmaDatabase provideOrmaDatabase(@NonNull Context context) {
        return OrmaDatabase.builder(context).build();
    }

    @Provides
    @Singleton
    KarutaRepository provideKarutaRepository(@NonNull Context context,
                                             @NonNull SharedPreferences preferences,
                                             @NonNull OrmaDatabase orma) {
        return new KarutaRepositoryImpl(context, preferences, orma);
    }

    @Provides
    @Singleton
    KarutaQuizRepository provideKarutaQuizRepository(@NonNull OrmaDatabase orma) {
        return new KarutaQuizRepositoryImpl(orma);
    }

    @Provides
    @Singleton
    KarutaExamRepository provideExamRepository(@NonNull OrmaDatabase orma) {
        return new KarutaExamRepositoryImpl(orma);
    }
}
