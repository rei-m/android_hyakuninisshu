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

package me.rei_m.hyakuninisshu.presentation.karuta.enums;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.model.karuta.Color;

public enum ColorFilter implements SpinnerItem {
    ALL(R.string.color_not_select, null),
    BLUE(R.string.color_blue, Color.BLUE),
    PINK(R.string.color_pink, Color.PINK),
    YELLOW(R.string.color_yellow, Color.YELLOW),
    GREEN(R.string.color_green, Color.GREEN),
    ORANGE(R.string.color_orange, Color.ORANGE);

    public static ColorFilter get(int ordinal) {
        return values()[ordinal];
    }

    private final int resId;

    private final Color color;

    ColorFilter(@StringRes int resId, @Nullable Color color) {
        this.resId = resId;
        this.color = color;
    }

    @Override
    public String label(@NonNull Resources res) {
        return res.getString(resId);
    }

    @Nullable
    public Color value() {
        return color;
    }
}
