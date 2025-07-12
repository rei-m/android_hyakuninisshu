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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "karuta_exam_table")
data class KarutaExamData(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "took_exam_date") val tookExamDate: Date,
    @ColumnInfo(name = "total_question_count") val totalQuestionCount: Int,
    @ColumnInfo(name = "average_answer_time") val averageAnswerTime: Float,
)
