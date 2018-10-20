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
package me.rei_m.hyakuninisshu.domain.model.karuta

import me.rei_m.hyakuninisshu.domain.AbstractEntity
import me.rei_m.hyakuninisshu.util.SPACE

/**
 * 上の句.
 */
class KamiNoKu(
    identifier: KamiNoKuIdentifier,
    val first: Phrase,
    val second: Phrase,
    val third: Phrase
) : AbstractEntity<KamiNoKuIdentifier>(identifier) {

    val kanji: String = "${first.kanji}$SPACE${second.kanji}$SPACE${third.kanji}"

    val kana: String = "${first.kana}$SPACE${second.kana}$SPACE${third.kana}"

    override fun toString() = "KamiNoKu(first=$first, second=$second, third=$third, kanji='$kanji', kana='$kana')"
}
