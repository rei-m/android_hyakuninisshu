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
package me.rei_m.hyakuninisshu.presentation.helper.bindingadapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.presentation.helper.GlideApp

@BindingAdapter("karutaSrc")
fun setKarutaSrc(view: ImageView, resIdString: String?) {
    resIdString ?: return
    val context = view.context.applicationContext
    val resId = context.resources.getIdentifier(
        "karuta_$resIdString",
        "drawable",
        context.packageName
    )

    GlideApp.with(view.context)
        .load(resId)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}

@BindingAdapter("isCorrect")
fun setIsCorrect(imageView: ImageView, isCorrect: Boolean?) {
    isCorrect ?: return
    val resId = if (isCorrect) R.drawable.check_correct else R.drawable.check_incorrect
    GlideApp.with(imageView.context).load(resId).dontAnimate().into(imageView)
}
