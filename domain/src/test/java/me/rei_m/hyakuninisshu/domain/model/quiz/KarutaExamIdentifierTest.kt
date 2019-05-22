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
package me.rei_m.hyakuninisshu.domain.model.quiz

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class KarutaExamIdentifierTest {

    private lateinit var karutaExamIdentifier: KarutaExamIdentifier

    @Before
    fun setUp() {
        karutaExamIdentifier = KarutaExamIdentifier(1L)
    }

    @Test
    fun createInstance() {
        assertThat(karutaExamIdentifier.value).isEqualTo(1L)
    }
}
