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

package me.rei_m.hyakuninisshu.domain

abstract class AbstractEntity<T : Entity<T, I>, I : EntityIdentifier> constructor(private val identifier: I) : Entity<T, I> {

    override fun identifier(): I {
        return identifier
    }

    override fun hashCode(): Int {
        return identifier.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return !(this !== other && !(other is Entity<*, *> && identifier == other.identifier()))
    }
}