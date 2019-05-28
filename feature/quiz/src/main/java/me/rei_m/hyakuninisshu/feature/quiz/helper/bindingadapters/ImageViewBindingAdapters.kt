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
package me.rei_m.hyakuninisshu.feature.quiz.helper.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import me.rei_m.hyakuninisshu.feature.corecomponent.R
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.GlideApp

@BindingAdapter("isCorrect")
fun setIsCorrect(imageView: ImageView, isCorrect: Boolean?) {
    isCorrect ?: return
    val resId = if (isCorrect) R.drawable.check_correct else R.drawable.check_incorrect
    GlideApp.with(imageView.context).load(resId).dontAnimate().into(imageView)
}
