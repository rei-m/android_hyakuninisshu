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

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
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
    private val database: AppDatabase,
    private val ioContext: CoroutineContext = Dispatchers.IO,
) : KarutaRepository {
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun initialize() {
        withContext(ioContext) {
            val karutaJsonVersion =
                preferences.getInt(
                    KarutaJsonConstant.KEY_KARUTA_JSON_VERSION,
                    0,
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
                inputStream.close()

                val karutaDataList = KarutaJsonAdaptor.convert(stringBuilder.toString())

                if (database.karutaDao().count() == 0) {
                    database.karutaDao().insertKarutas(karutaDataList)
                } else {
                    val orgKarutaDataList = database.karutaDao().findAll()
                    orgKarutaDataList.filter { it.isEdited != true }.forEach {
                        karutaDataList.find { karutaData -> karutaData.no == it.no }
                            ?.let { karuta ->
                                database.karutaDao().upsert(karuta)
                            }
                    }
                }

                preferences.edit {
                    putInt(
                        KarutaJsonConstant.KEY_KARUTA_JSON_VERSION,
                        KarutaJsonConstant.KARUTA_VERSION,
                    )
                }
            }
        }
    }

    override suspend fun findByNo(karutaNo: KarutaNo): Karuta =
        withContext(ioContext) {
            database.karutaDao().findByNo(no = karutaNo.value).toModel()
        }

    override suspend fun findAll(): List<Karuta> =
        withContext(ioContext) {
            database.karutaDao().findAll().map { it.toModel() }
        }

    override suspend fun findAllWithCondition(
        fromNo: KarutaNo,
        toNo: KarutaNo,
        kimarijis: List<Kimariji>,
        colors: List<KarutaColor>,
    ): List<Karuta> =
        withContext(ioContext) {
            database
                .karutaDao()
                .findAllWithCondition(
                    fromNo = fromNo.value,
                    toNo = toNo.value,
                    kimarijis = kimarijis.map { it.value },
                    colors = colors.map { it.value },
                ).map { it.toModel() }
        }

    override suspend fun findAllWithNo(karutaNoList: List<KarutaNo>): List<Karuta> =
        withContext(ioContext) {
            database
                .karutaDao()
                .findAllWithNo(
                    nos = karutaNoList.map { it.value },
                ).map { it.toModel() }
        }

    override suspend fun save(karuta: Karuta) =
        withContext(ioContext) {
            val orgData = database.karutaDao().findByNo(karuta.no.value)
            val karutaData = KarutaData(
                no = orgData.no,
                creator = orgData.creator,
                firstKana = karuta.kamiNoKu.shoku.kana,
                firstKanji = karuta.kamiNoKu.shoku.kanji,
                secondKana = karuta.kamiNoKu.niku.kana,
                secondKanji = karuta.kamiNoKu.niku.kanji,
                thirdKana = karuta.kamiNoKu.sanku.kana,
                thirdKanji = karuta.kamiNoKu.sanku.kanji,
                fourthKana = karuta.shimoNoKu.shiku.kana,
                fourthKanji = karuta.shimoNoKu.shiku.kanji,
                fifthKana = karuta.shimoNoKu.goku.kana,
                fifthKanji = karuta.shimoNoKu.goku.kanji,
                kimariji = orgData.kimariji,
                imageNo = orgData.imageNo,
                translation = orgData.translation,
                color = orgData.color,
                colorNo = orgData.colorNo,
                torifuda = orgData.torifuda,
                isEdited = true
            )
            database.karutaDao().update(karutaData)

            return@withContext
        }
}

fun KarutaData.toModel(): Karuta {
    val karutaNo = KarutaNo(no)
    val shoku = Verse(kana = firstKana, kanji = firstKanji)
    val niku = Verse(kana = secondKana, kanji = secondKanji)
    val sanku = Verse(kana = thirdKana, kanji = thirdKanji)
    val shiku = Verse(kana = fourthKana, kanji = fourthKanji)
    val goku = Verse(kana = fifthKana, kanji = fifthKanji)

    return Karuta(
        id = KarutaId(no),
        no = karutaNo,
        creator = creator,
        kamiNoKu = KamiNoKu(karutaNo = karutaNo, shoku = shoku, niku = niku, sanku = sanku),
        shimoNoKu = ShimoNoKu(karutaNo = karutaNo, shiku = shiku, goku = goku),
        kimariji = Kimariji.forValue(kimariji),
        imageNo = KarutaImageNo(imageNo),
        translation = translation,
        color = KarutaColor.forValue(color),
    )
}
