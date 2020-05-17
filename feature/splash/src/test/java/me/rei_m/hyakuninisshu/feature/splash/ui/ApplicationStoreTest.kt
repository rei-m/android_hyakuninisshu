/*
 * Copyright (c) 2020. Rei Matsushita
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
package me.rei_m.hyakuninisshu.feature.splash.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.schedulers.Schedulers
import me.rei_m.hyakuninisshu.action.AppDispatcher
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.application.StartApplicationAction
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ApplicationStoreTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dispatcher: Dispatcher
    private lateinit var store: ApplicationStore

    @Before
    fun setUp() {
        dispatcher = AppDispatcher(Schedulers.trampoline())
        store = ApplicationStore(dispatcher)
    }

    @After
    fun tearDown() {
        store.dispose()
    }

    @Test
    fun initialState_isCorrect() {
        assertThat(store.isReady.value).isFalse()
        assertThat(store.unhandledErrorEvent.value).isNull()
    }

    @Test
    fun state_receivedStartApplicationAction_isCorrect() {
        dispatcher.dispatch(StartApplicationAction.createSuccess())
        assertThat(store.isReady.value).isTrue()
        assertThat(store.unhandledErrorEvent.value).isNull()
    }

    @Test
    fun state_receivedFailedStartApplicationAction_isCorrect() {
        dispatcher.dispatch(StartApplicationAction.createError(IllegalStateException()))
        assertThat(store.isReady.value).isFalse()
        assertThat(store.unhandledErrorEvent.value).isNotNull
    }
}