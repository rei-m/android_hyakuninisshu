/*
 * Copyright (c) 2025. Rei Matsushita
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

package me.rei_m.hyakuninisshu.feature.corecomponent.helper.bindingadapters

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView

@BindingAdapter("textKamiNoKuKana", "kimariji")
fun setTextKamiNoKuKana(
    view: MaterialTextView,
    kamiNoKu: String?,
    kimariji: Int?,
) {
    if (kamiNoKu == null || kimariji == null) {
        return
    }

    val spaceCount =
        if (kamiNoKu.substring(0, kimariji).contains("　")) {
            1
        } else {
            0
        }

    val ssb = SpannableStringBuilder().append(kamiNoKu)
    ssb.setSpan(
        ForegroundColorSpan(Color.RED),
        0,
        kimariji + spaceCount,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
    )
    view.text = ssb
}
