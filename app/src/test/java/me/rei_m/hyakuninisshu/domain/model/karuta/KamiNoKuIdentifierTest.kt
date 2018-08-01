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

package me.rei_m.hyakuninisshu.domain.model.karuta

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KamiNoKuIdentifierTest {

    @Test
    fun equals() {
        val thisValue = KamiNoKuIdentifier(1)
        val thatValue = KamiNoKuIdentifier(1)
        assertThat(thisValue == thatValue).isTrue()
    }

    @Test
    fun notEquals() {
        val thisValue = KamiNoKuIdentifier(1)
        val thatValue = KamiNoKuIdentifier(2)
        assertThat(thisValue == thatValue).isFalse()
    }
}