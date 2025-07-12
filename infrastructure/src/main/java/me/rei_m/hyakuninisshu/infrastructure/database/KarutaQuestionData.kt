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
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "karuta_question_table",
    indices = [Index("correct_karuta_no"), Index("no")],
    foreignKeys = [
        ForeignKey(
            entity = KarutaData::class,
            parentColumns = arrayOf("no"),
            childColumns = arrayOf("correct_karuta_no"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class KarutaQuestionData(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "no") val no: Int,
    @ColumnInfo(name = "correct_karuta_no") val correctKarutaNo: Int,
    @ColumnInfo(name = "start_date") val startDate: Date?,
    @ColumnInfo(name = "selected_karuta_no") val selectedKarutaNo: Int?,
    @ColumnInfo(name = "is_correct") val isCorrect: Boolean?,
    @ColumnInfo(name = "answer_time") val answerTime: Long?,
)
