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

package me.rei_m.hyakuninisshu.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        KarutaData::class,
        KarutaExamData::class,
        KarutaExamWrongKarutaNoData::class,
        KarutaQuestionData::class,
        KarutaQuestionChoiceData::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun karutaDao(): KarutaDao

    abstract fun karutaQuestionDao(): KarutaQuestionDao

    abstract fun karutaQuestionChoiceDao(): KarutaQuestionChoiceDao

    abstract fun karutaExamDao(): KarutaExamDao

    abstract fun karutaExamWrongKarutaNoDao(): KarutaExamWrongKarutaNoDao

    companion object {
        const val DB_NAME = "hyakuninisshu_database"
    }
}
