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

package me.rei_m.hyakuninisshu.state.application.action

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ApplicationActionCreatorTest {
    private lateinit var actionCreator: ApplicationActionCreator

    private lateinit var repository: KarutaRepository

    @Before
    fun setUp() {
        repository = mock {}
        actionCreator = ApplicationActionCreator(repository)
    }

    @Test
    fun start_success() = runBlocking {
        whenever(repository.initialize()).thenAnswer { }

        val actual = actionCreator.start()
        assertThat(actual).isInstanceOf(StartApplicationAction.Success::class.java)
        return@runBlocking
    }

    @Test
    fun start_failure() = runBlocking {
        whenever(repository.initialize()).thenThrow(RuntimeException())

        val actual = actionCreator.start()
        assertThat(actual).isInstanceOf(StartApplicationAction.Failure::class.java)
        return@runBlocking
    }
}
