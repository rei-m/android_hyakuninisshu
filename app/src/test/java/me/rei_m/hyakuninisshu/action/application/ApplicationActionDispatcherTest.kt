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
package me.rei_m.hyakuninisshu.action.application

import com.nhaarman.mockito_kotlin.check
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.helper.DirectCoroutineContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ApplicationActionDispatcherTest {

    private lateinit var actionDispatcher: ApplicationActionDispatcher

    private lateinit var repository: KarutaRepository

    private lateinit var dispatcher: Dispatcher

    @Before
    fun setUp() {
        dispatcher = mock {}
        repository = mock {}
        actionDispatcher = ApplicationActionDispatcher(repository, dispatcher, DirectCoroutineContext)
    }

    @Test
    fun start() {
        whenever(repository.initialize()).thenAnswer { }

        actionDispatcher.start()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartApplicationAction::class.java)
            assertThat(it.error).isNull()
        })
    }
}
