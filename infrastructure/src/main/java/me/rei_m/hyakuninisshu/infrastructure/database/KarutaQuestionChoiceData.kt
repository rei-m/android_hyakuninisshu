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

@Entity(
    tableName = "karuta_question_choice_table",
    indices = [Index("karuta_question_id"), Index("karuta_no")],
    foreignKeys = [
        ForeignKey(
            entity = KarutaQuestionData::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("karuta_question_id"),
            onDelete = ForeignKey.CASCADE,
        ), ForeignKey(
            entity = KarutaData::class,
            parentColumns = arrayOf("no"),
            childColumns = arrayOf("karuta_no"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class KarutaQuestionChoiceData(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "karuta_question_id") val karutaQuestionId: String,
    @ColumnInfo(name = "karuta_no") val karutaNo: Int,
    @ColumnInfo(name = "order") val order: Int,
)
