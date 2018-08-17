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

package me.rei_m.hyakuninisshu.presentation.materialedit

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.v4.app.FragmentActivity
import me.rei_m.hyakuninisshu.action.material.MaterialActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.ext.map
import me.rei_m.hyakuninisshu.ext.setIfNull
import me.rei_m.hyakuninisshu.presentation.ViewModelFactory
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import me.rei_m.hyakuninisshu.util.Event
import me.rei_m.hyakuninisshu.util.EventObserver
import javax.inject.Inject
import me.rei_m.hyakuninisshu.R

class MaterialEditViewModel(
    private val store: MaterialEditStore,
    private val actionDispatcher: MaterialActionDispatcher,
    private val karutaId: KarutaIdentifier,
    firstPhraseKanji: String?,
    firstPhraseKana: String?,
    secondPhraseKanji: String?,
    secondPhraseKana: String?,
    thirdPhraseKanji: String?,
    thirdPhraseKana: String?,
    fourthPhraseKanji: String?,
    fourthPhraseKana: String?,
    fifthPhraseKanji: String?,
    fifthPhraseKana: String?,
    private val navigator: Navigator
) {

    private val karuta: LiveData<Karuta?> = store.karuta

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

    private val _confirmEditEvent = MutableLiveData<Event<ConfirmMaterialEditDialogFragment>>()
    val confirmEditEvent: LiveData<Event<ConfirmMaterialEditDialogFragment>> = _confirmEditEvent

    private val _snackBarMessage = MutableLiveData<Event<Int>>()
    val snackBarMessage: LiveData<Event<Int>> = _snackBarMessage

    val unhandledErrorEvent: LiveData<Event<Unit>> = store.unhandledErrorEvent

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

    private val completeEditEventObserver = EventObserver<Unit> {
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
        store.completeEditEvent.observeForever(completeEditEventObserver)

        actionDispatcher.startEdit(karutaId)
    }

    fun onDestroyView() {
        karuta.removeObserver(karutaObserver)
        store.completeEditEvent.removeObserver(completeEditEventObserver)
    }

    fun updateMaterial() {
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
            _snackBarMessage.value = Event(R.string.text_message_edit_error)
            return
        }

        actionDispatcher.edit(
            karutaId,
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

    fun onClickEdit() {
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
            _snackBarMessage.value = Event(R.string.text_message_edit_error)
            return
        }

        _confirmEditEvent.value = Event(ConfirmMaterialEditDialogFragment.newInstance(
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
        ))
    }

    class Factory @Inject constructor(
        private val actionDispatcher: MaterialActionDispatcher,
        private val storeFactory: MaterialEditStore.Factory,
        private val navigator: Navigator
    ) : ViewModelFactory {

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

        fun create(activity: FragmentActivity, karutaId: KarutaIdentifier) = MaterialEditViewModel(
            obtainActivityStore(activity, MaterialEditStore::class.java, storeFactory),
            actionDispatcher,
            karutaId,
            firstPhraseKanji,
            firstPhraseKana,
            secondPhraseKanji,
            secondPhraseKana,
            thirdPhraseKanji,
            thirdPhraseKana,
            fourthPhraseKanji,
            fourthPhraseKana,
            fifthPhraseKanji,
            fifthPhraseKana,
            navigator
        )
    }
}
