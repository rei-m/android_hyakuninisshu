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
package me.rei_m.hyakuninisshu.infrastructure.database

import android.content.Context
import android.content.SharedPreferences
import me.rei_m.hyakuninisshu.domain.model.karuta.Color
import me.rei_m.hyakuninisshu.domain.model.karuta.ImageNo
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKu
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKuIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji
import me.rei_m.hyakuninisshu.domain.model.karuta.Phrase
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKu
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKuIdentifier
import java.io.BufferedReader
import java.io.InputStreamReader

class KarutaRepositoryImpl(
    private val context: Context,
    private val preferences: SharedPreferences,
    private val orma: OrmaDatabase
) : KarutaRepository {

    override fun initialize() {

        val karutaJsonVersion = preferences.getInt(
            KarutaJsonConstant.KEY_KARUTA_JSON_VERSION,
            0
        )

        if (karutaJsonVersion < KarutaJsonConstant.KARUTA_VERSION) {
            val inputStream = context.assets.open("karuta_list.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (true) {
                line = reader.readLine()
                if (line == null) {
                    break
                }
                stringBuilder.append(line)
            }
            reader.close()

            val karutaSchemaList = KarutaJsonAdaptor.convert(stringBuilder.toString())

            orma.transactionSync {
                karutaSchemaList.forEach {
                    val count = KarutaSchema.relation(orma).selector().idEq(it.id)
                        .and()
                        .where("isEdited = ?", true).count()
                    if (count == 0) {
                        KarutaSchema.relation(orma).upsert(it)
                    }
                }

                preferences.edit()
                    .putInt(KarutaJsonConstant.KEY_KARUTA_JSON_VERSION, KarutaJsonConstant.KARUTA_VERSION)
                    .apply()
            }
        }
    }

    override fun list(color: Color?): Karutas {
        val relation = KarutaSchema.relation(orma)
        var selector = relation.selector()
        if (color != null) {
            selector = selector.and().where("color = ?", color.value)
        }

        return Karutas(selector
            .orderBy(relation.schema.id.orderInAscending())
            .toList()
            .map { it.toModel() })
    }

    override fun findIds(
        fromIdentifier: KarutaIdentifier,
        toIdentifier: KarutaIdentifier,
        color: Color?,
        kimariji: Kimariji?
    ): KarutaIds {
        val relation = KarutaSchema.relation(orma)
        var selector = relation.selector()
            .idGe(fromIdentifier.value.toLong())
            .and()
            .idLe(toIdentifier.value.toLong())

        if (color != null) {
            selector = selector.and().where("color = ?", color.value)
        }

        if (kimariji != null) {
            selector = selector.and().where("kimariji = ?", kimariji.value)
        }

        return KarutaIds(selector
            .orderBy(relation.schema.id.orderInAscending())
            .toList()
            .map { karutaSchema -> KarutaIdentifier(karutaSchema.id.toInt()) })
    }

    override fun findIds(): KarutaIds {
        val relation = KarutaSchema.relation(orma)
        return KarutaIds(KarutaSchema.relation(orma).selector()
            .orderBy(relation.schema.id.orderInAscending())
            .toList()
            .map { karutaSchema -> KarutaIdentifier(karutaSchema.id.toInt()) })
    }

    override fun findBy(karutaId: KarutaIdentifier): Karuta? {
        return KarutaSchema.relation(orma).selector()
            .idEq(karutaId.value.toLong())
            .firstOrNull()?.toModel()
    }

    override fun store(karuta: Karuta) {
        val kamiNoKu = karuta.kamiNoKu
        val shimoNoKu = karuta.shimoNoKu

        KarutaSchema.relation(orma).updater()
            .idEq(karuta.identifier().value.toLong())
            .firstKana(kamiNoKu.first.kana)
            .firstKanji(kamiNoKu.first.kanji)
            .secondKana(kamiNoKu.second.kana)
            .secondKanji(kamiNoKu.second.kanji)
            .thirdKana(kamiNoKu.third.kana)
            .thirdKanji(kamiNoKu.third.kanji)
            .fourthKana(shimoNoKu.fourth.kana)
            .fourthKanji(shimoNoKu.fourth.kanji)
            .fifthKana(shimoNoKu.fifth.kana)
            .fifthKanji(shimoNoKu.fifth.kanji)
            .isEdited(true)
            .execute()
    }
}

private fun KarutaSchema.toModel(): Karuta {

    val identifier = KarutaIdentifier(id.toInt())

    val firstPart = Phrase(firstKana, firstKanji)
    val secondPart = Phrase(secondKana, secondKanji)
    val thirdPart = Phrase(thirdKana, thirdKanji)
    val fourthPart = Phrase(fourthKana, fourthKanji)
    val fifthPart = Phrase(fifthKana, fifthKanji)

    val kamiNoKu = KamiNoKu(KamiNoKuIdentifier(identifier.value), firstPart, secondPart, thirdPart)
    val shimoNoKu = ShimoNoKu(ShimoNoKuIdentifier(identifier.value), fourthPart, fifthPart)

    val kimariji = Kimariji.forValue(kimariji)

    val color = Color.forValue(color)

    return Karuta(
        identifier,
        creator,
        kamiNoKu,
        shimoNoKu,
        kimariji,
        ImageNo(imageNo),
        translation,
        color
    )
}
