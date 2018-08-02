/*
 * Copyright (c) 2018. Rei Matsushita
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

package me.rei_m.hyakuninisshu.domain.model.quiz

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds

/**
 * 力試しのコレクション.
 */
class KarutaExams(private val values: List<KarutaExam>) {

    /**
     * 直近の力試し.
     */
    val recent: KarutaExam? = values.firstOrNull()

    /**
     * 過去の力試しの結果から間違えた問題の歌のIDの集合.
     */
    val totalWrongKarutaIds: KarutaIds = values.fold(mutableListOf<KarutaIdentifier>()) { karutaIdList, karutaExam ->
        karutaExam.result.wrongKarutaIds.values.forEach { wrongKarutaId ->
            if (!karutaIdList.contains(wrongKarutaId)) {
                karutaIdList.add(wrongKarutaId)
            }
        }
        karutaIdList
    }.let {
        KarutaIds(it)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val that = other as KarutaExams?

        return values == that!!.values
    }

    override fun hashCode() = values.hashCode()

    override fun toString() = "KarutaExams(values=$values)"
}
