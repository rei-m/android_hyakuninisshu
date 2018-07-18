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

package me.rei_m.hyakuninisshu.infrastructure.database

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table

import java.util.Date

@Table
data class KarutaExamSchema(
        @Setter("id") @PrimaryKey(autoincrement = true) var id: Long = 0,
        @Setter("tookExamDate") @Column var tookExamDate: Date,
        @Setter("totalQuizCount") @Column var totalQuizCount: Int,
        @Setter("averageAnswerTime") @Column var averageAnswerTime: Float
) {
    companion object {
        fun relation(orma: OrmaDatabase): KarutaExamSchema_Relation {
            return orma.relationOfKarutaExamSchema()
        }
    }

    fun getWrongKarutas(orma: OrmaDatabase): ExamWrongKarutaSchema_Relation {
        return orma.relationOfExamWrongKarutaSchema().examSchemaEq(this)
    }
}
