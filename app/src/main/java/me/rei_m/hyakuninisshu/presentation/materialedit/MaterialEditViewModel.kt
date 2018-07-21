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

package me.rei_m.hyakuninisshu.presentation.materialedit

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.view.View
import me.rei_m.hyakuninisshu.action.material.MaterialActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.ext.LiveDataExt
import me.rei_m.hyakuninisshu.ext.MutableLiveDataExt
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import me.rei_m.hyakuninisshu.presentation.helper.SingleLiveEvent
import javax.inject.Inject
import me.rei_m.hyakuninisshu.R as Res

class MaterialEditViewModel(
        private val karutaStore: MaterialEditStore,
        private val actionDispatcher: MaterialActionDispatcher,
        private val navigator: Navigator,
        firstPhraseKanji: String?,
        firstPhraseKana: String?,
        secondPhraseKanji: String?,
        secondPhraseKana: String?,
        thirdPhraseKanji: String?,
        thirdPhraseKana: String?,
        fourthPhraseKanji: String?,
        fourthPhraseKana: String?,
        fifthPhraseKanji: String?,
        fifthPhraseKana: String?
) : LiveDataExt, MutableLiveDataExt {

    private val karuta: LiveData<Karuta?> = karutaStore.karuta

    val karutaNo: LiveData<Int?> = karuta.map { it?.identifier()?.value }

    val creator: LiveData<String?> = karuta.map { it?.creator }

    val kimariji: LiveData<Int?> = karuta.map { it?.kimariji?.value }

    val firstPhraseKanji = MutableLiveData<String>()

    val firstPhraseKana = MutableLiveData<String>()

    val secondPhraseKanji = MutableLiveData<String>()

    val secondPhraseKana = MutableLiveData<String>()

    val thirdPhraseKanji = MutableLiveData<String>()

    val thirdPhraseKana = MutableLiveData<String>()

    val fourthPhraseKanji = MutableLiveData<String>()

    val fourthPhraseKana = MutableLiveData<String>()

    val fifthPhraseKanji = MutableLiveData<String>()

    val fifthPhraseKana = MutableLiveData<String>()

    val confirmEditEvent: SingleLiveEvent<ConfirmMaterialEditDialogFragment> = SingleLiveEvent()

    val snackBarMessage: SingleLiveEvent<Int> = SingleLiveEvent()

    private val karutaObserver: Observer<Karuta?> = Observer {
        it ?: return@Observer
        this.firstPhraseKanji.setIfNull(it.kamiNoKu.first.kanji)
        this.firstPhraseKana.setIfNull(it.kamiNoKu.first.kana)
        this.secondPhraseKanji.setIfNull(it.kamiNoKu.second.kanji)
        this.secondPhraseKana.setIfNull(it.kamiNoKu.second.kana)
        this.thirdPhraseKanji.setIfNull(it.kamiNoKu.third.kanji)
        this.thirdPhraseKana.setIfNull(it.kamiNoKu.third.kana)
        this.fourthPhraseKanji.setIfNull(it.shimoNoKu.fourth.kanji)
        this.fourthPhraseKana.setIfNull(it.shimoNoKu.fourth.kana)
        this.fifthPhraseKanji.setIfNull(it.shimoNoKu.fifth.kanji)
        this.fifthPhraseKana.setIfNull(it.shimoNoKu.fifth.kana)
    }

    private val completeEditEventObserver: Observer<Void> = Observer {
        navigator.back()
    }

    init {
        this.firstPhraseKanji.value = firstPhraseKanji
        this.firstPhraseKana.value = firstPhraseKana
        this.secondPhraseKanji.value = secondPhraseKanji
        this.secondPhraseKana.value = secondPhraseKana
        this.thirdPhraseKanji.value = thirdPhraseKanji
        this.thirdPhraseKana.value = thirdPhraseKana
        this.fourthPhraseKanji.value = fourthPhraseKanji
        this.fourthPhraseKana.value = fourthPhraseKana
        this.fifthPhraseKanji.value = fifthPhraseKanji
        this.fifthPhraseKana.value = fifthPhraseKana
        karuta.observeForever(karutaObserver)
        karutaStore.completeEditEvent.observeForever(completeEditEventObserver)
    }

    fun onDestroyView() {
        karuta.removeObserver(karutaObserver)
        karutaStore.completeEditEvent.removeObserver(completeEditEventObserver)
    }

    fun start(karutaId: KarutaIdentifier) {
        if (karuta.value == null) {
            actionDispatcher.startEdit(karutaId)
        }
    }

    fun updateMaterial() {
        actionDispatcher.edit(
                requireNotNull(karuta.value).identifier(),
                requireNotNull(firstPhraseKanji.value),
                requireNotNull(firstPhraseKana.value),
                requireNotNull(secondPhraseKanji.value),
                requireNotNull(secondPhraseKana.value),
                requireNotNull(thirdPhraseKanji.value),
                requireNotNull(thirdPhraseKana.value),
                requireNotNull(fourthPhraseKanji.value),
                requireNotNull(fourthPhraseKana.value),
                requireNotNull(fifthPhraseKanji.value),
                requireNotNull(fifthPhraseKana.value)
        )
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClickEdit(view: View) {
        val firstPhraseKanji = this.firstPhraseKanji.value ?: ""
        val firstPhraseKana = this.firstPhraseKana.value ?: ""
        val secondPhraseKanji = this.secondPhraseKanji.value ?: ""
        val secondPhraseKana = this.secondPhraseKana.value ?: ""
        val thirdPhraseKanji = this.thirdPhraseKanji.value ?: ""
        val thirdPhraseKana = this.thirdPhraseKana.value ?: ""
        val fourthPhraseKanji = this.fourthPhraseKanji.value ?: ""
        val fourthPhraseKana = this.fourthPhraseKana.value ?: ""
        val fifthPhraseKanji = this.fifthPhraseKanji.value ?: ""
        val fifthPhraseKana = this.fifthPhraseKana.value ?: ""
        if (
                firstPhraseKanji.isBlank() || firstPhraseKana.isBlank() ||
                secondPhraseKanji.isBlank() || secondPhraseKana.isBlank() ||
                thirdPhraseKanji.isBlank() || thirdPhraseKana.isBlank() ||
                fourthPhraseKanji.isBlank() || fourthPhraseKana.isBlank() ||
                fifthPhraseKanji.isBlank() || fifthPhraseKana.isBlank()
        ) {
            snackBarMessage.value = Res.string.text_message_edit_error
            return
        }
        confirmEditEvent.value = ConfirmMaterialEditDialogFragment.newInstance(
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
        )
    }

    class Factory @Inject constructor(private val actionDispatcher: MaterialActionDispatcher,
                                      private val navigator: Navigator) {

        var firstPhraseKanji: String? = null
        var firstPhraseKana: String? = null
        var secondPhraseKanji: String? = null
        var secondPhraseKana: String? = null
        var thirdPhraseKanji: String? = null
        var thirdPhraseKana: String? = null
        var fourthPhraseKanji: String? = null
        var fourthPhraseKana: String? = null
        var fifthPhraseKanji: String? = null
        var fifthPhraseKana: String? = null

        fun create(store: MaterialEditStore): MaterialEditViewModel = MaterialEditViewModel(
                store,
                actionDispatcher,
                navigator,
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
        )
    }
}
