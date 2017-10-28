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

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

/**
 * 歌の色.
 */
public enum Color implements ValueObject {
    BLUE("blue"),
    PINK("pink"),
    YELLOW("yellow"),
    GREEN("green"),
    ORANGE("orange");

    public static Color forValue(String value) {
        for (Color color : values()) {
            if (color.value().equals(value)) {
                return color;
            }
        }
        throw new AssertionError("no enum found. value is " + value);
    }

    private final String value;

    Color(@NonNull String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "Color{" +
                "value='" + value + '\'' +
                "} " + super.toString();
    }
}
