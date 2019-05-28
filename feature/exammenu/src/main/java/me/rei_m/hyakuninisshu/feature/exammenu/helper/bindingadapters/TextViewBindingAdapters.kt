/*
 * Copyright (c) 2018. Rei Matsushita
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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.feature.exammenu.helper.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.diffString
import me.rei_m.hyakuninisshu.feature.exammenu.R
import java.util.*

@BindingAdapter("averageAnswerSec")
fun setAverageAnswerTime(
    view: TextView,
    averageAnswerSec: Float?
) {
    averageAnswerSec ?: return
    val context = view.context.applicationContext
    val averageAnswerTimeString = String.format(Locale.JAPAN, "%.2f", averageAnswerSec)
    view.text = context.getString(R.string.seconds, averageAnswerTimeString)
}
