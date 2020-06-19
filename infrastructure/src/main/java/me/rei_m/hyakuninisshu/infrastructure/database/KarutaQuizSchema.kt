/*
 * Copyright (c) 2020. Rei Matsushita
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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.infrastructure.database

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
import java.util.Date

@Table
data class KarutaQuizSchema(
    @Setter("id") @PrimaryKey(autoincrement = true) var id: Long = 0,
    @Setter("quizId") @Column(indexed = true) var quizId: String,
    @Setter("no") @Column var no: Int,
    @Setter("collectId") @Column var collectId: Long,
    @Setter("startDate") @Column var startDate: Date? = null,
    @Setter("choiceNo") @Column var choiceNo: Int? = null,
    @Setter("isCollect") @Column var isCollect: Boolean = false,
    @Setter("answerTime") @Column var answerTime: Long? = 0
) {
    fun getChoices(orma: OrmaDatabase): KarutaQuizChoiceSchema_Relation {
        return orma.relationOfKarutaQuizChoiceSchema()
            .karutaQuizSchemaEq(this)
            .orderByOrderNoAsc()
    }

    companion object {
        fun relation(orma: OrmaDatabase): KarutaQuizSchema_Relation {
            return orma.relationOfKarutaQuizSchema()
        }
    }
}
