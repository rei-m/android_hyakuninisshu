/*
 * Copyright (c) 2019. Rei Matsushita
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
package me.rei_m.hyakuninisshu.action.material

import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.karuta.Color
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaterialActionCreator @Inject constructor(
    private val karutaRepository: KarutaRepository,
    private val dispatcher: Dispatcher
) {
    /**
     * 指定した色の該当する歌をすべて取り出す.
     *
     *  @param color 五色絞り込み条件
     */
    fun fetch(color: Color?) {
        try {
            val karutaList = karutaRepository.list(color)
            dispatcher.dispatch(FetchMaterialAction.createSuccess(karutaList))
        } catch (e: Exception) {
            dispatcher.dispatch(FetchMaterialAction.createError(e))
        }
    }

    /**
     * 指定した歌の編集を開始する.
     *
     * @param karutaId 歌ID.
     */
    fun startEdit(karutaId: KarutaIdentifier) {
        try {
            val karuta = karutaRepository.findBy(karutaId)
                ?: throw NoSuchElementException(karutaId.toString())
            dispatcher.dispatch(StartEditMaterialAction.createSuccess(karuta))
        } catch (e: Exception) {
            dispatcher.dispatch(StartEditMaterialAction.createError(e))
        }
    }

    /**
     * 指定した歌の編集をする.
     *
     * @param karutaId 歌ID.
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
     */
    fun edit(
        karutaId: KarutaIdentifier,
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
    ) {
        try {
            val karuta = karutaRepository.findBy(karutaId)
                ?: throw NoSuchElementException(karutaId.toString())
            karuta.updatePhrase(
                firstPhraseKanji,
                firstPhraseKana,
                secondPhraseKanji,
                secondPhraseKana,
                thirdPhraseKanji,
                thirdPhraseKana,
                fourthPhraseKanji,
                fourthPhraseKana,
                fifthPhraseKanji,
                fifthPhraseKana
            ).let {
                karutaRepository.store(it)
            }
            dispatcher.dispatch(EditMaterialAction.createSuccess(karuta))
        } catch (e: Exception) {
            dispatcher.dispatch(EditMaterialAction.createError(e))
        }
    }
}