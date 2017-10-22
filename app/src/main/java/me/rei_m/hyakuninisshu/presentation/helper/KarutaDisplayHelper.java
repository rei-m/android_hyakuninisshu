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

package me.rei_m.hyakuninisshu.presentation.helper;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;

public class KarutaDisplayHelper {

    private KarutaDisplayHelper() {
    }

    public static String convertNumberToString(@NonNull Context context, int number) {

        if (number == Karuta.NUMBER_OF_KARUTA) {
            return context.getString(R.string.karuta_number, context.getString(R.string.hundred));
        }

        Resources resources = context.getResources();
        String[] numArray = resources.getStringArray(R.array.number);

        int doubleDigits = number / 10;
        int singleDigits = number % 10;

        StringBuilder sb = new StringBuilder();
        if (0 < doubleDigits) {
            if (1 < doubleDigits) {
                sb.append(numArray[doubleDigits - 1]);
            }
            sb.append(resources.getString(R.string.ten));
        }

        if (0 < singleDigits) {
            sb.append(numArray[singleDigits - 1]);
        }

        return context.getString(R.string.karuta_number, sb.toString());
    }

    public static String convertKimarijiToString(@NonNull Context context, int kimariji) {
        Resources resources = context.getResources();
        String[] kimarijiArray = resources.getStringArray(R.array.kimariji);
        return kimarijiArray[kimariji - 1];
    }
}
