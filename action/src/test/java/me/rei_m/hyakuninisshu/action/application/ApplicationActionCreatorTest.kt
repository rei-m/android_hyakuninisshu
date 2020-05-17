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

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
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
    fun start() {
        whenever(repository.initialize()).thenAnswer { }

        val actual = actionCreator.start()
        assertThat(actual).isInstanceOf(StartApplicationAction::class.java)
        assertThat(actual.error).isNull()
    }
}
