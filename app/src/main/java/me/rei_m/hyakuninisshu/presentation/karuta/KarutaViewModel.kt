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

package me.rei_m.hyakuninisshu.presentation.karuta

import android.arch.lifecycle.LiveData
import android.support.v4.app.FragmentActivity
import me.rei_m.hyakuninisshu.action.karuta.KarutaActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.ext.LiveDataExt
import me.rei_m.hyakuninisshu.presentation.ViewModelFactory
import javax.inject.Inject

class KarutaViewModel(
        store: KarutaStore,
        actionDispatcher: KarutaActionDispatcher,
        karutaId: KarutaIdentifier
) : LiveDataExt {

    val isLoading: LiveData<Boolean> = store.karuta.map { it == null }

    val karutaNo: LiveData<Int?> = store.karuta.map { it?.identifier()?.value }

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
            actionDispatcher.fetch(karutaId)
        }
    }

    class Factory @Inject constructor(private val actionDispatcher: KarutaActionDispatcher,
                                      private val storeFactory: KarutaStore.Factory) : ViewModelFactory {
        fun create(activity: FragmentActivity, karutaId: KarutaIdentifier): KarutaViewModel {
            val store = obtainActivityStore(activity, KarutaStore::class.java, storeFactory)
            return KarutaViewModel(store, actionDispatcher, karutaId)
        }
    }
}
