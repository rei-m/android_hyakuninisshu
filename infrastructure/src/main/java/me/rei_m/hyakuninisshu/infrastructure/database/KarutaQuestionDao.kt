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
import androidx.room.Update

@Dao
interface KarutaQuestionDao {
    @Query("SELECT COUNT(*) from karuta_question_table")
    suspend fun count(): Int

    @Query("SELECT * from karuta_question_table WHERE `id` = :id")
    fun findById(id: String): KarutaQuestionData?

    @Query("SELECT id from karuta_question_table WHERE `no` = :no")
    fun findIdByNo(no: Int): String?

    @Query("SELECT * from karuta_question_table ORDER BY `no` ASC")
    fun findAll(): List<KarutaQuestionData>

    @Insert
    suspend fun insertKarutaQuestions(karutaQuestions: List<KarutaQuestionData>)

    @Update
    suspend fun updateKarutaQuestion(karutaQuestion: KarutaQuestionData)

    @Query("DELETE FROM karuta_question_table")
    suspend fun deleteAll()
}
