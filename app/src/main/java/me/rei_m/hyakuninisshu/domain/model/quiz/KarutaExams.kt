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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.domain.model.quiz

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds

/**
 * 力試しのコレクション.
 */
class KarutaExams(private val values: List<KarutaExam>) {

    /**
     * すべての力試し.
     */
    val all: List<KarutaExam> = values

    /**
     * 直近の力試し.
     */
    val recent: KarutaExam? = values.firstOrNull()

    /**
     * 過去の力試しの結果から間違えた問題の歌のIDの集合.
     */
    val totalWrongKarutaIds: KarutaIds = KarutaIds(values.fold(mutableListOf()) { karutaIdList, karutaExam ->
        karutaExam.result.wrongKarutaIds.values.forEach { wrongKarutaId ->
            if (!karutaIdList.contains(wrongKarutaId)) {
                karutaIdList.add(wrongKarutaId)
            }
        }
        karutaIdList
    })

    override fun equals(other: Any?): Boolean {
        other as? KarutaExams ?: return false
        return values == other.values
    }

    override fun hashCode() = values.hashCode()

    override fun toString() = "KarutaExams(values=$values)"
}
