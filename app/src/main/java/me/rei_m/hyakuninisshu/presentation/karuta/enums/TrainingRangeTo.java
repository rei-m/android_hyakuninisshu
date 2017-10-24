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
import android.support.annotation.StringRes;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;

public enum TrainingRangeTo implements SpinnerItem {
    TEN(R.string.training_range_10, 10),
    TWENTY(R.string.training_range_20, 20),
    THIRTY(R.string.training_range_30, 30),
    FORTY(R.string.training_range_40, 40),
    FIFTY(R.string.training_range_50, 50),
    SIXTY(R.string.training_range_60, 60),
    SEVENTY(R.string.training_range_70, 70),
    EIGHTY(R.string.training_range_80, 80),
    NINETY(R.string.training_range_90, 90),
    ONE_HUNDRED(R.string.training_range_100, 100);

    public static TrainingRangeTo get(int ordinal) {
        return values()[ordinal];
    }

    private final int resId;

    private final KarutaIdentifier id;

    TrainingRangeTo(@StringRes int resId, int id) {
        this.resId = resId;
        this.id = new KarutaIdentifier(id);
    }

    @Override
    public String label(@NonNull Resources res) {
        return res.getString(resId);
    }

    public KarutaIdentifier identifier() {
        return id;
    }
}
