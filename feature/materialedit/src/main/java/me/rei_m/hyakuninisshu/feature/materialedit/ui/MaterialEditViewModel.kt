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
package me.rei_m.hyakuninisshu.feature.materialedit.ui

import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import me.rei_m.hyakuninisshu.feature.corecomponent.ui.AbstractViewModel
import me.rei_m.hyakuninisshu.feature.materialedit.R
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.core.Event
import me.rei_m.hyakuninisshu.state.material.action.MaterialActionCreator
import me.rei_m.hyakuninisshu.state.material.model.Material
import me.rei_m.hyakuninisshu.state.material.store.EditMaterialStore
import javax.inject.Inject

class MaterialEditViewModel(
    dispatcher: Dispatcher,
    private val actionCreator: MaterialActionCreator,
    private val store: EditMaterialStore,
    private val orgMaterial: Material,
    handle: SavedStateHandle
) : AbstractViewModel(dispatcher) {

    val creatorAndNo = "${orgMaterial.noTxt} / ${orgMaterial.creator}"

    val firstPhraseKanji = handle.getLiveData(KEY_FIRST_KANJI, orgMaterial.shokuKanji)
    val firstPhraseKana = handle.getLiveData(KEY_FIRST_KANA, orgMaterial.shokuKana)

    val secondPhraseKanji = handle.getLiveData(KEY_SECOND_KANJI, orgMaterial.nikuKanji)
    val secondPhraseKana = handle.getLiveData(KEY_SECOND_KANA, orgMaterial.nikuKana)

    val thirdPhraseKanji = handle.getLiveData(KEY_THIRD_KANJI, orgMaterial.sankuKanji)
    val thirdPhraseKana = handle.getLiveData(KEY_THIRD_KANA, orgMaterial.sankuKana)

    val fourthPhraseKanji = handle.getLiveData(KEY_FOURTH_KANJI, orgMaterial.shikuKanji)
    val fourthPhraseKana = handle.getLiveData(KEY_FOURTH_KANA, orgMaterial.shikuKana)

    val fifthPhraseKanji = handle.getLiveData(KEY_FIFTH_KANJI, orgMaterial.gokuKanji)
    val fifthPhraseKana = handle.getLiveData(KEY_FIFTH_KANA, orgMaterial.gokuKana)

    private val _confirmEditEvent = MutableLiveData<Event<Unit>>()
    val confirmEditEvent: LiveData<Event<Unit>> = _confirmEditEvent

    val onCompleteEditEvent = store.onCompleteEditEvent

    private val _snackBarMessage = MutableLiveData<Event<Int>>()
    val snackBarMessage: LiveData<Event<Int>> = _snackBarMessage

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    fun onClickEdit() {
        if (
            this.firstPhraseKanji.value.isNullOrBlank() || this.firstPhraseKana.value.isNullOrBlank() ||
            this.secondPhraseKanji.value.isNullOrBlank() || this.secondPhraseKana.value.isNullOrBlank() ||
            this.thirdPhraseKanji.value.isNullOrBlank() || this.thirdPhraseKana.value.isNullOrBlank() ||
            this.fourthPhraseKanji.value.isNullOrBlank() || this.fourthPhraseKana.value.isNullOrBlank() ||
            this.fifthPhraseKanji.value.isNullOrBlank() || this.fifthPhraseKana.value.isNullOrBlank()
        ) {
            _snackBarMessage.value = Event(R.string.text_message_edit_error)
            return
        }
        _confirmEditEvent.value = Event(Unit)
    }

    fun onSubmitEdit() {
        dispatchAction {
            actionCreator.edit(
                orgMaterial.no,
                firstPhraseKanji.value!!,
                firstPhraseKana.value!!,
                secondPhraseKanji.value!!,
                secondPhraseKana.value!!,
                thirdPhraseKanji.value!!,
                thirdPhraseKana.value!!,
                fourthPhraseKanji.value!!,
                fourthPhraseKana.value!!,
                fifthPhraseKanji.value!!,
                fifthPhraseKana.value!!
            )
        }
    }

    class Factory @Inject constructor(
        owner: SavedStateRegistryOwner,
        private val dispatcher: Dispatcher,
        private val actionCreator: MaterialActionCreator,
        private val store: EditMaterialStore
    ) : AbstractSavedStateViewModelFactory(owner, null) {
        lateinit var orgMaterial: Material

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T = MaterialEditViewModel(
            dispatcher,
            actionCreator,
            store,
            orgMaterial,
            handle
        ) as T
    }

    companion object {
        private const val KEY_FIRST_KANJI = "firstKanji"
        private const val KEY_FIRST_KANA = "firstKana"
        private const val KEY_SECOND_KANJI = "secondKanji"
        private const val KEY_SECOND_KANA = "secondKana"
        private const val KEY_THIRD_KANJI = "thirdKanji"
        private const val KEY_THIRD_KANA = "thirdKana"
        private const val KEY_FOURTH_KANJI = "fourthKanji"
        private const val KEY_FOURTH_KANA = "fourthKana"
        private const val KEY_FIFTH_KANJI = "fifthKanji"
        private const val KEY_FIFTH_KANA = "fifthKana"
    }
}
