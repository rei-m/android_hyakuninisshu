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

package me.rei_m.hyakuninisshu.infrastructure.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaRepository
import me.rei_m.hyakuninisshu.domain.question.model.ExamRepository
import me.rei_m.hyakuninisshu.domain.question.model.QuestionRepository
import me.rei_m.hyakuninisshu.infrastructure.database.AppDatabase
import me.rei_m.hyakuninisshu.infrastructure.database.ExamRepositoryImpl
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaRepositoryImpl
import me.rei_m.hyakuninisshu.infrastructure.database.QuestionRepositoryImpl
import javax.inject.Singleton

@Module
class InfrastructureModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase =
        Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DB_NAME,
            ).build()

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideKarutaRepository(
        context: Context,
        preferences: SharedPreferences,
        appDatabase: AppDatabase,
    ): KarutaRepository = KarutaRepositoryImpl(context, preferences, appDatabase)

    @Provides
    @Singleton
    fun provideQuestionRepository(appDatabase: AppDatabase): QuestionRepository = QuestionRepositoryImpl(appDatabase)

    @Provides
    @Singleton
    fun provideExamRepository(appDatabase: AppDatabase): ExamRepository = ExamRepositoryImpl(appDatabase)

    companion object {
        private const val KEY_PREFERENCES = "B84UqpLA7W"
    }
}
