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

package me.rei_m.hyakuninisshu.action.material

import io.reactivex.Single
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.ext.SingleExt
import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import javax.inject.Inject

class MaterialActionDispatcher @Inject constructor(
        private val dispatcher: Dispatcher,
        private val karutaRepository: KarutaRepository
) : SingleExt {
    fun fetch(colorFilter: ColorFilter) {
        karutaRepository.list(colorFilter.value).subscribeNew { karutas ->
            dispatcher.dispatch(FetchMaterialAction(karutas, colorFilter))
        }
    }

    fun startEdit(karutaIdentifier: KarutaIdentifier) {
        karutaRepository.findBy(karutaIdentifier).subscribeNew { karuta ->
            dispatcher.dispatch(StartEditMaterialAction(karuta))
        }
    }

    fun edit(karutaIdentifier: KarutaIdentifier,
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

        karutaRepository.findBy(karutaIdentifier).flatMap<Karuta> { karuta ->
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
