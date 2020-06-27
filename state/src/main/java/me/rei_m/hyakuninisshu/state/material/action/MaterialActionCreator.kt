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

package me.rei_m.hyakuninisshu.state.material.action

import android.content.Context
import me.rei_m.hyakuninisshu.domain.karuta.model.KamiNoKu
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaColor
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaRepository
import me.rei_m.hyakuninisshu.domain.karuta.model.Kimariji
import me.rei_m.hyakuninisshu.domain.karuta.model.ShimoNoKu
import me.rei_m.hyakuninisshu.domain.karuta.model.Verse
import me.rei_m.hyakuninisshu.state.core.ext.toMaterial
import me.rei_m.hyakuninisshu.state.material.model.ColorFilter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaterialActionCreator @Inject constructor(
    private val context: Context,
    private val karutaRepository: KarutaRepository
) {
    /**
     * 指定した色の該当する歌をすべて取り出す.
     *
     *  @param color 五色絞り込み条件
     *
     *  @return FetchMaterialAction
     */
    suspend fun fetchMaterialList(color: ColorFilter?) = try {
        val colors =
            if (color?.value == null) KarutaColor.values().toList() else listOf(color.value)
        val karutaList = karutaRepository.findAllWithCondition(
            fromNo = KarutaNo.MIN,
            toNo = KarutaNo.MAX,
            kimarijis = Kimariji.values().toList(),
            colors = colors
        )

        FetchMaterialListAction.Success(karutaList.map { it.toMaterial(context) })
    } catch (e: Exception) {
        FetchMaterialListAction.Failure(e)
    }

    /**
     * 指定した歌の編集をする.
     *
     * @param karutaNo 歌番号.
     * @param firstPhraseKanji 初句 漢字.
     * @param firstPhraseKana 初句 かな.
     * @param secondPhraseKanji 二句 漢字.
     * @param secondPhraseKana 二句 かな.
     * @param thirdPhraseKanji 三句 漢字.
     * @param thirdPhraseKana 三句 かな.
     * @param fourthPhraseKanji 四句 漢字.
     * @param fourthPhraseKana 四句 かな.
     * @param fifthPhraseKanji 五句 漢字.
     * @param fifthPhraseKana 五句 かな.
     * @return EditMaterialAction
     */
    suspend fun edit(
        karutaNo: Int,
        firstPhraseKanji: String,
        firstPhraseKana: String,
        secondPhraseKanji: String,
        secondPhraseKana: String,
        thirdPhraseKanji: String,
        thirdPhraseKana: String,
        fourthPhraseKanji: String,
        fourthPhraseKana: String,
        fifthPhraseKanji: String,
        fifthPhraseKana: String
    ) = try {
        val karuta = karutaRepository.findByNo(KarutaNo(karutaNo))
        karuta.update(
            kamiNoKu = KamiNoKu(
                karutaNo = karuta.no,
                shoku = Verse(
                    kana = firstPhraseKana,
                    kanji = firstPhraseKanji
                ),
                niku = Verse(
                    kana = secondPhraseKana,
                    kanji = secondPhraseKanji
                ),
                sanku = Verse(
                    kana = thirdPhraseKana,
                    kanji = thirdPhraseKanji
                )
            ),
            shimoNoKu = ShimoNoKu(
                karutaNo = karuta.no,
                shiku = Verse(
                    kana = fourthPhraseKana,
                    kanji = fourthPhraseKanji
                ),
                goku = Verse(
                    kana = fifthPhraseKana,
                    kanji = fifthPhraseKanji
                )
            )
        ).let {
            karutaRepository.save(it)
        }
        EditMaterialAction.Success(karuta.toMaterial(context))
    } catch (e: Exception) {
        EditMaterialAction.Failure(e)
    }
}
