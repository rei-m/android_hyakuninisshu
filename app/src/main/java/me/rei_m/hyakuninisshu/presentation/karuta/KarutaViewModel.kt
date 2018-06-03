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

package me.rei_m.hyakuninisshu.presentation.karuta

import android.arch.lifecycle.LiveData
import me.rei_m.hyakuninisshu.action.karuta.KarutaActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.ext.LiveDataExt
import javax.inject.Inject

class KarutaViewModel(
        karutaStore: KarutaStore,
        private val actionDispatcher: KarutaActionDispatcher
) : LiveDataExt {

    private val karuta: LiveData<Karuta> = karutaStore.karuta

    val karutaNo: LiveData<Int> = karuta.map { it.identifier().value }

    val karutaImageNo: LiveData<String> = karuta.map { it.imageNo.value }

    val creator: LiveData<String> = karuta.map { it.creator }

    val kimariji: LiveData<Int> = karuta.map { it.kimariji.value }

    val kamiNoKuKanji: LiveData<String> = karuta.map { it.kamiNoKu.kanji }

    val shimoNoKuKanji: LiveData<String> = karuta.map { it.shimoNoKu.kanji }

    val kamiNoKuKana: LiveData<String> = karuta.map { it.kamiNoKu.kana }

    val shimoNoKuKana: LiveData<String> = karuta.map { it.shimoNoKu.kana }

    val translation: LiveData<String> = karuta.map { it.translation }

    fun start(karutaId: KarutaIdentifier) {
        if (karuta.value == null) {
            actionDispatcher.fetch(karutaId)
        }
    }

    class Factory @Inject constructor(private val actionDispatcher: KarutaActionDispatcher) {
        fun create(store: KarutaStore): KarutaViewModel = KarutaViewModel(store, actionDispatcher)
    }
}
