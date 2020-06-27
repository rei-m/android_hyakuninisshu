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

package me.rei_m.hyakuninisshu.infrastructure.database

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.rei_m.hyakuninisshu.domain.karuta.model.KamiNoKu
import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaColor
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaId
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaImageNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaRepository
import me.rei_m.hyakuninisshu.domain.karuta.model.Kimariji
import me.rei_m.hyakuninisshu.domain.karuta.model.ShimoNoKu
import me.rei_m.hyakuninisshu.domain.karuta.model.Verse
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.coroutines.CoroutineContext

class KarutaRepositoryImpl(
    private val context: Context,
    private val preferences: SharedPreferences,
    private val orma: OrmaDatabase,
    private val ioContext: CoroutineContext = Dispatchers.IO
) : KarutaRepository {
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun initialize() {
        withContext(ioContext) {
            val karutaJsonVersion = preferences.getInt(
                KarutaJsonConstant.KEY_KARUTA_JSON_VERSION,
                0
            )

            if (karutaJsonVersion < KarutaJsonConstant.KARUTA_VERSION) {
                val inputStream = context.assets.open("karuta_list_v_2.json")
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
                        .putInt(
                            KarutaJsonConstant.KEY_KARUTA_JSON_VERSION,
                            KarutaJsonConstant.KARUTA_VERSION
                        )
                        .apply()
                }
            }
        }
    }

    override suspend fun findByNo(karutaNo: KarutaNo): Karuta = withContext(ioContext) {
        KarutaSchema.relation(orma).selector()
            .idEq(karutaNo.value.toLong())
            .first().toModel()
    }

    override suspend fun findAll(): List<Karuta> {
        return withContext(ioContext) {
            val relation = KarutaSchema.relation(orma)
            relation.selector()
                .orderBy(relation.schema.id.orderInAscending())
                .toList()
                .map { it.toModel() }
        }
    }

    override suspend fun findAllWithCondition(
        fromNo: KarutaNo,
        toNo: KarutaNo,
        kimarijis: List<Kimariji>,
        colors: List<KarutaColor>
    ): List<Karuta> {
        return withContext(ioContext) {
            val relation = KarutaSchema.relation(orma)
            var selector = relation.selector()
                .idBetween(fromNo.value.toLong(), toNo.value.toLong())
            selector = selector.and().kimarijiIn(kimarijis.map { it.value })
            selector = selector.and().colorIn(colors.map { it.value })
            selector
                .orderBy(relation.schema.id.orderInAscending())
                .toList()
                .map { it.toModel() }
        }
    }

    override suspend fun findAllWithNo(karutaNoList: List<KarutaNo>): List<Karuta> {
        return withContext(ioContext) {
            val relation = KarutaSchema.relation(orma)
            val selector = relation.selector()
                .idIn(karutaNoList.map { it.value.toLong() })
            selector
                .orderBy(relation.schema.id.orderInAscending())
                .toList()
                .map { it.toModel() }
        }
    }

    override suspend fun save(karuta: Karuta) = withContext(ioContext) {
        val kamiNoKu = karuta.kamiNoKu
        val shimoNoKu = karuta.shimoNoKu

        KarutaSchema.relation(orma).updater()
            .idEq(karuta.identifier.value.toLong())
            .firstKana(kamiNoKu.shoku.kana)
            .firstKanji(kamiNoKu.shoku.kanji)
            .secondKana(kamiNoKu.niku.kana)
            .secondKanji(kamiNoKu.niku.kanji)
            .thirdKana(kamiNoKu.sanku.kana)
            .thirdKanji(kamiNoKu.sanku.kanji)
            .fourthKana(shimoNoKu.shiku.kana)
            .fourthKanji(shimoNoKu.shiku.kanji)
            .fifthKana(shimoNoKu.goku.kana)
            .fifthKanji(shimoNoKu.goku.kanji)
            .isEdited(true)
            .execute()

        return@withContext
    }
}

private fun KarutaSchema.toModel(): Karuta {

    val identifier = KarutaId(id.toInt())
    val karutaNo = KarutaNo(id.toInt())
    val firstPart = Verse(firstKana, firstKanji)
    val secondPart = Verse(secondKana, secondKanji)
    val thirdPart = Verse(thirdKana, thirdKanji)
    val fourthPart = Verse(fourthKana, fourthKanji)
    val fifthPart = Verse(fifthKana, fifthKanji)

    val kamiNoKu = KamiNoKu(karutaNo, firstPart, secondPart, thirdPart)
    val shimoNoKu = ShimoNoKu(karutaNo, fourthPart, fifthPart)

    val kimariji = Kimariji.forValue(kimariji)

    val color = KarutaColor.forValue(color)

    return Karuta(
        identifier,
        karutaNo,
        creator,
        kamiNoKu,
        shimoNoKu,
        kimariji,
        KarutaImageNo(imageNo),
        translation,
        color
    )
}
