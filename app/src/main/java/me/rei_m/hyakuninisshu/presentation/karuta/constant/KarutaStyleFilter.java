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

package me.rei_m.hyakuninisshu.presentation.karuta.constant;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaStyle;

public enum KarutaStyleFilter implements SpinnerItem {
    KANA(R.string.display_style_kana, KarutaStyle.KANA),
    KANJI(R.string.display_style_kanji, KarutaStyle.KANJI);

    public static KarutaStyleFilter get(int ordinal) {
        return values()[ordinal];
    }

    private final int resId;

    private final KarutaStyle value;

    KarutaStyleFilter(@StringRes int resId, @NonNull KarutaStyle value) {
        this.resId = resId;
        this.value = value;
    }

    @Override
    public String code() {
        return value.value();
    }

    @Override
    public String label(Resources res) {
        return res.getString(resId);
    }

    public KarutaStyle value() {
        return value;
    }
}
