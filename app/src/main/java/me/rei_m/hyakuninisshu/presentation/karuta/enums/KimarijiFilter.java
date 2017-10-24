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
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji;

public enum KimarijiFilter implements SpinnerItem {
    ALL(R.string.kimariji_not_select, null),
    ONE(R.string.kimariji_1, Kimariji.ONE),
    TWO(R.string.kimariji_2, Kimariji.TWO),
    THREE(R.string.kimariji_3, Kimariji.THREE),
    FOUR(R.string.kimariji_4, Kimariji.FOUR),
    FIVE(R.string.kimariji_5, Kimariji.FIVE),
    SIX(R.string.kimariji_6, Kimariji.SIX);

    public static KimarijiFilter get(int ordinal) {
        return values()[ordinal];
    }

    private final int resId;

    private final Kimariji value;

    KimarijiFilter(@StringRes int resId, @Nullable Kimariji value) {
        this.resId = resId;
        this.value = value;
    }

    @Override
    public String label(@NonNull Resources res) {
        return res.getString(resId);
    }

    @Nullable
    public Kimariji value() {
        return value;
    }
}
