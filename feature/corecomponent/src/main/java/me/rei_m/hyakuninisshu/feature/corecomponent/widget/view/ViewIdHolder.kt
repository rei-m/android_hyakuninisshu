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
package me.rei_m.hyakuninisshu.feature.corecomponent.widget.view

import java.util.concurrent.atomic.AtomicInteger

object ViewIdHolder {

    private val nextGeneratedId = AtomicInteger(1)

    /**
     * Generate a valueOrNull suitable for use in [.setId].
     * This valueOrNull will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID valueOrNull
     */
    fun generateViewId(): Int {
        while (true) {
            val result = nextGeneratedId.get()
            var newValue = result + 1
            if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
            if (nextGeneratedId.compareAndSet(result, newValue)) {
                return result
            }
        }
    }
}
