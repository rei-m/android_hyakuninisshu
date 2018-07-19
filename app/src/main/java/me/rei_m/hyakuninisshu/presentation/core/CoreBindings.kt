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

package me.rei_m.hyakuninisshu.presentation.core

import android.databinding.BindingAdapter
import android.support.annotation.DimenRes
import android.widget.ImageView
import android.widget.TextView
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.ext.IntExt
import me.rei_m.hyakuninisshu.presentation.helper.GlideApp
import me.rei_m.hyakuninisshu.presentation.widget.view.VerticalSingleLineTextView

object CoreBindings : IntExt {

    @JvmStatic
    @BindingAdapter("verticalText")
    fun setVerticalText(view: VerticalSingleLineTextView,
                        text: String?) {
        view.drawText(text)
    }

    @JvmStatic
    @BindingAdapter("verticalTextSize")
    fun setVerticalTextSize(view: VerticalSingleLineTextView,
                            @DimenRes dimenId: Int) {
        view.setTextSize(dimenId)
    }

    @JvmStatic
    @BindingAdapter("verticalTextSizeByPx")
    fun setVerticalTextSizeByPx(view: VerticalSingleLineTextView,
                                textSize: Int?) {
        textSize ?: let { return }
        view.setTextSizeByPx(textSize)
    }

    @JvmStatic
    @BindingAdapter("textForQuiz", "textPosition")
    fun setTextForQuiz(view: TextView,
                       text: String?,
                       textPosition: Int?) {
        text ?: let { return }
        textPosition ?: let { return }
        if (text.length < textPosition) {
            return
        }
        view.text = text.substring(textPosition - 1, textPosition)
    }

    @JvmStatic
    @BindingAdapter("karutaNo", "kimariji")
    fun setKarutaNoAndKimariji(view: TextView,
                               karutaNo: Int?,
                               kimariji: Int?) {
        karutaNo ?: let { return }
        kimariji ?: let { return }

        if (kimariji <= 0) {
            return
        }

        val context = view.context.applicationContext
        val text = "${karutaNo.toKarutaNoStr(context)} / ${kimariji.toKimarijiStr(context)}"
        view.text = text
    }

    @JvmStatic
    @BindingAdapter("isCorrect")
    fun setIsCorrect(imageView: ImageView,
                     isCorrect: Boolean?) {
        isCorrect ?: let { return }
        val resId = if (isCorrect) R.drawable.check_correct else R.drawable.check_incorrect
        GlideApp.with(imageView.context).load(resId).dontAnimate().into(imageView)
    }
}
