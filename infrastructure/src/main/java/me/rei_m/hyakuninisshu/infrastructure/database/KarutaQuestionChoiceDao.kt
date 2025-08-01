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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KarutaQuestionChoiceDao {
    @Query("SELECT * from karuta_question_choice_table WHERE karuta_question_id = :karutaQuestionId ORDER BY `order` ASC")
    fun findAllByKarutaQuestionId(karutaQuestionId: String): List<KarutaQuestionChoiceData>

    @Query("SELECT * from karuta_question_choice_table ORDER BY `order` ASC")
    fun findAll(): List<KarutaQuestionChoiceData>

    @Insert
    suspend fun insertKarutaQuestionChoices(karutaQuestionChoices: List<KarutaQuestionChoiceData>)
}
