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

package me.rei_m.hyakuninisshu.action.material

import io.reactivex.Single
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.ext.SingleExt
import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaterialActionDispatcher @Inject constructor(
        private val dispatcher: Dispatcher,
        private val karutaRepository: KarutaRepository
) : SingleExt {

    /**
     * 指定した色の該当する歌をすべて取り出す.
     *
     *  @param colorFilter 五色絞り込み条件
     */
    fun fetch(colorFilter: ColorFilter) {
        karutaRepository.list(colorFilter.value).subscribeNew {
            dispatcher.dispatch(FetchMaterialAction(it))
        }
    }

    /**
     * 指定した歌の編集を開始する.
     *
     * @param karutaId 歌ID.
     */
    fun startEdit(karutaId: KarutaIdentifier) {
        karutaRepository.findBy(karutaId).subscribeNew {
            dispatcher.dispatch(StartEditMaterialAction(it))
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
    fun edit(karutaId: KarutaIdentifier,
             firstPhraseKanji: String,
             firstPhraseKana: String,
             secondPhraseKanji: String,
             secondPhraseKana: String,
             thirdPhraseKanji: String,
             thirdPhraseKana: String,
             fourthPhraseKanji: String,
             fourthPhraseKana: String,
             fifthPhraseKanji: String,
             fifthPhraseKana: String) {

        karutaRepository.findBy(karutaId).flatMap<Karuta> { karuta ->
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
                return@flatMap karutaRepository.store(it).andThen(Single.just(karuta))
            }
        }.subscribeNew {
            dispatcher.dispatch(EditMaterialAction(it))
        }
    }
}