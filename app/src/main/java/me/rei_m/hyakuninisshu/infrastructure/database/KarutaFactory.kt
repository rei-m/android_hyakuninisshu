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

import me.rei_m.hyakuninisshu.domain.model.karuta.Color
import me.rei_m.hyakuninisshu.domain.model.karuta.ImageNo
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKu
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKuIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji
import me.rei_m.hyakuninisshu.domain.model.karuta.Phrase
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKu
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKuIdentifier

internal object KarutaFactory {

    fun create(schema: KarutaSchema): Karuta {

        val identifier = KarutaIdentifier(schema.id.toInt())

        val firstPart = Phrase(schema.firstKana, schema.firstKanji)
        val secondPart = Phrase(schema.secondKana, schema.secondKanji)
        val thirdPart = Phrase(schema.thirdKana, schema.thirdKanji)
        val fourthPart = Phrase(schema.fourthKana, schema.fourthKanji)
        val fifthPart = Phrase(schema.fifthKana, schema.fifthKanji)

        val kamiNoKu = KamiNoKu(KamiNoKuIdentifier(identifier.value), firstPart, secondPart, thirdPart)
        val shimoNoKu = ShimoNoKu(ShimoNoKuIdentifier(identifier.value), fourthPart, fifthPart)

        val translation = schema.translation

        val kimariji = Kimariji.forValue(schema.kimariji)

        val color = Color.forValue(schema.color)

        return Karuta(identifier,
                schema.creator,
                kamiNoKu,
                shimoNoKu,
                kimariji,
                ImageNo(schema.imageNo),
                translation,
                color)
    }
}
