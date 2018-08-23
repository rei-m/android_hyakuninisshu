/*
 * Copyright (c) 2018. Rei Matsushita
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

package me.rei_m.hyakuninisshu.infrastructure.di

import android.content.Context
import android.content.SharedPreferences

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository
import me.rei_m.hyakuninisshu.infrastructure.database.*

@Module
class InfrastructureModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideOrmaProvider(context: Context): OrmaProvider {
        return OrmaProvider(context)
    }

    @Provides
    @Singleton
    fun provideKarutaRepository(context: Context,
                                preferences: SharedPreferences,
                                orma: OrmaProvider): KarutaRepository {
        return KarutaRepositoryImpl(context, preferences, orma.ormaDatabase)
    }

    @Provides
    @Singleton
    fun provideKarutaQuizRepository(orma: OrmaProvider): KarutaQuizRepository {
        return KarutaQuizRepositoryImpl(orma.ormaDatabase)
    }

    @Provides
    @Singleton
    fun provideExamRepository(orma: OrmaProvider): KarutaExamRepository {
        return KarutaExamRepositoryImpl(orma.ormaDatabase)
    }

    companion object {
        private const val KEY_PREFERENCES = "B84UqpLA7W"
    }
}
