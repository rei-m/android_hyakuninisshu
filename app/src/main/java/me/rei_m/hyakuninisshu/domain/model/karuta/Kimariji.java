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

package me.rei_m.hyakuninisshu.domain.model.karuta;

import me.rei_m.hyakuninisshu.domain.ValueObject;

/**
 * 歌の決まり字.
 */
public enum Kimariji implements ValueObject {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    private final int value;

    Kimariji(int value) {
        this.value = value;
    }

    public static Kimariji forValue(int value) {
        for (Kimariji kimariji : values()) {
            if (kimariji.value() == value) {
                return kimariji;
            }
        }
        throw new AssertionError("no enum found. value is " + value);
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return "Kimariji{" +
                "value=" + value +
                '}';
    }
}
