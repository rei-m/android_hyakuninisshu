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
package me.rei_m.hyakuninisshu.action.material

import me.rei_m.hyakuninisshu.action.Action
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta

class StartEditMaterialAction private constructor(
    val karuta: Karuta?,
    override val error: Throwable? = null
) : Action {

    override val name = "StartEditMaterialAction"

    override fun toString() = if (isSucceeded) "$name(karuta=$karuta)" else "$name(error=$error)"

    companion object {
        fun createSuccess(karuta: Karuta) = StartEditMaterialAction(karuta)
        fun createError(error: Throwable) = StartEditMaterialAction(null, error)
    }
}
