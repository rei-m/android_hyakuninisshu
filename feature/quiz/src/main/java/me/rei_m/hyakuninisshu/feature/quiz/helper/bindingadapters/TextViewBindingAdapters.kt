/*
 * Copyright (c) 2019. Rei Matsushita
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
package me.rei_m.hyakuninisshu.feature.quiz.helper.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("textForQuiz", "textPosition")
fun setTextForQuiz(
    view: TextView,
    text: String?,
    textPosition: Int?
) {
    text ?: return
    textPosition ?: return
    if (text.length < textPosition) {
        return
    }
    view.text = text.substring(textPosition - 1, textPosition)
}
