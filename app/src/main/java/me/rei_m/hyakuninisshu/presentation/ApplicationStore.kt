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

package me.rei_m.hyakuninisshu.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.application.StartApplicationAction
import javax.inject.Inject

class ApplicationStore @Inject constructor(dispatcher: Dispatcher) : ViewModel() {

    private val isRedayLiveData = MutableLiveData<Boolean>()
    val isReady: LiveData<Boolean> = isRedayLiveData

    private val disposable = CompositeDisposable()

    init {
        isRedayLiveData.value = false
        disposable.add(dispatcher.on(StartApplicationAction::class.java).subscribe {
            isRedayLiveData.value = true
        })
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    class Factory @Inject constructor(private val dispatcher: Dispatcher) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ApplicationStore(dispatcher) as T
        }
    }
}
