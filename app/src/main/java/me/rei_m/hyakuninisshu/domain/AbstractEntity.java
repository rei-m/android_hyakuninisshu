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

package me.rei_m.hyakuninisshu.domain;

import android.support.annotation.NonNull;

public abstract class AbstractEntity<T extends Entity<T, I>, I extends EntityIdentifier> implements Entity<T, I> {

    private final I identifier;

    protected AbstractEntity(@NonNull I identifier) {
        this.identifier = identifier;
    }

    @Override
    public I identifier() {
        return identifier;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T clone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error("clone not supported");
        }
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        return this == that ||
                !(that == null || !(that instanceof Entity)) && identifier.equals(((Entity) that).identifier());
    }
}
