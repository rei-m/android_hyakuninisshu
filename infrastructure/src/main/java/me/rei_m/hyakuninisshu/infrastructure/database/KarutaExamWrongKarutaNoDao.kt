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
interface KarutaExamWrongKarutaNoDao {
    @Query("SELECT * from karuta_exam_wrong_karuta_no_table ORDER BY `karuta_exam_id` DESC, `karuta_no` ASC")
    fun findAll(): List<KarutaExamWrongKarutaNoData>

    @Query("SELECT * from karuta_exam_wrong_karuta_no_table WHERE karuta_exam_id = :karutaExamId ORDER BY `karuta_no` ASC")
    fun findAllWithExamId(karutaExamId: Long): List<KarutaExamWrongKarutaNoData>

    @Insert
    suspend fun insert(karutaExamWrongKarutaNoList: List<KarutaExamWrongKarutaNoData>): List<Long>
}
