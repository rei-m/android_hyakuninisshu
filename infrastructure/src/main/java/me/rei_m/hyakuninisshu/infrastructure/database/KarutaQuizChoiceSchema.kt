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

import com.github.gfx.android.orma.SingleAssociation
import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table

@Table
data class KarutaQuizChoiceSchema(
    @Setter("karutaQuizSchema") @Column(indexed = true) var karutaQuizSchema: SingleAssociation<KarutaQuizSchema>,
    @Setter("karutaId") @Column var karutaId: Long,
    @Setter("orderNo") @Column(indexed = true) var orderNo: Long
) {
    companion object {
        fun relation(orma: OrmaDatabase): KarutaQuizChoiceSchema_Relation {
            return orma.relationOfKarutaQuizChoiceSchema()
        }
    }
}
