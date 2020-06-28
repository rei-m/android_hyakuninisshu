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

package me.rei_m.hyakuninisshu.state.application.store

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.schedulers.Schedulers
import me.rei_m.hyakuninisshu.state.application.action.StartApplicationAction
import me.rei_m.hyakuninisshu.state.core.ActionDispatcher
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.core.Event
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
        dispatcher = ActionDispatcher(Schedulers.trampoline())
        store = ApplicationStore(dispatcher)
    }

    @After
    fun tearDown() {
        store.dispose()
    }

    @Test
    fun initialState() {
        assertThat(store.onReadyEvent.value).isNull()
        assertThat(store.isFailure.value).isFalse()
    }

    @Test
    fun state_receivedStartApplicationAction() {
        dispatcher.dispatch(StartApplicationAction.Success())
        assertThat(store.onReadyEvent.value).isInstanceOf(Event::class.java)
        assertThat(store.isFailure.value).isFalse()
    }

    @Test
    fun state_receivedFailedStartApplicationAction() {
        dispatcher.dispatch(StartApplicationAction.Failure(RuntimeException()))
        assertThat(store.onReadyEvent.value).isNull()
        assertThat(store.isFailure.value).isTrue()
    }
}
