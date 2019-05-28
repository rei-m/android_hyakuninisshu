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
package me.rei_m.hyakuninisshu.feature.karuta.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import me.rei_m.hyakuninisshu.action.karuta.KarutaActionCreator
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.map
import me.rei_m.hyakuninisshu.feature.corecomponent.lifecycle.AbstractViewModel
import kotlin.coroutines.CoroutineContext

class KarutaViewModel(
    mainContext: CoroutineContext,
    private val store: KarutaStore,
    actionCreator: KarutaActionCreator,
    karutaId: KarutaIdentifier
) : AbstractViewModel(mainContext) {

    val isLoading: LiveData<Boolean> = store.karuta.map { it == null }

    val karutaNo: LiveData<Int?> = store.karuta.map { it?.identifier?.value }

    val karutaImageNo: LiveData<String?> = store.karuta.map { it?.imageNo?.value }

    val creator: LiveData<String?> = store.karuta.map { it?.creator }

    val kimariji: LiveData<Int?> = store.karuta.map { it?.kimariji?.value }

    val kamiNoKuKanji: LiveData<String?> = store.karuta.map { it?.kamiNoKu?.kanji }

    val shimoNoKuKanji: LiveData<String?> = store.karuta.map { it?.shimoNoKu?.kanji }

    val kamiNoKuKana: LiveData<String?> = store.karuta.map { it?.kamiNoKu?.kana }

    val shimoNoKuKana: LiveData<String?> = store.karuta.map { it?.shimoNoKu?.kana }

    val translation: LiveData<String?> = store.karuta.map { it?.translation }

    val notFoundKarutaEvent = store.notFoundKarutaEvent

    init {
        if (store.karuta.value == null) {
            launch {
                actionCreator.fetch(karutaId)
            }
        }
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory(
        private val mainContext: CoroutineContext,
        private val store: KarutaStore,
        private val actionCreator: KarutaActionCreator,
        private val karutaId: KarutaIdentifier
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return KarutaViewModel(mainContext, store, actionCreator, karutaId) as T
        }
    }
}
