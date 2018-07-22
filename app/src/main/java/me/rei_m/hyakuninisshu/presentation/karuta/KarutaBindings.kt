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

package me.rei_m.hyakuninisshu.presentation.karuta

import android.databinding.BindingAdapter
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import me.rei_m.hyakuninisshu.ext.IntExt
import me.rei_m.hyakuninisshu.presentation.helper.GlideApp
import me.rei_m.hyakuninisshu.util.Constants

object KarutaBindings : IntExt {
    @JvmStatic
    @BindingAdapter("karutaNo")
    fun setKarutaNo(view: TextView, karutaNo: Int?) {
        if (karutaNo == null) {
            return
        }

        val context = view.context.applicationContext
        view.text = karutaNo.toKarutaNoStr(context)
    }

    @JvmStatic
    @BindingAdapter("karutaNo", "creator")
    fun setKarutaNoAndCreator(view: TextView,
                              karutaNo: Int?,
                              creator: String?) {

        if (karutaNo == null || creator == null) {
            return
        }

        val context = view.context.applicationContext
        val text = "${karutaNo.toKarutaNoStr(context)} / $creator"
        view.text = text
    }

    @JvmStatic
    @BindingAdapter("karutaSrc")
    fun setKarutaSrc(view: ImageView, resIdString: String?) {
        resIdString ?: return

        val context = view.context.applicationContext
        val resId = context.resources.getIdentifier("karuta_$resIdString", "drawable", context.packageName)

        GlideApp.with(view.context)
                .load(resId)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }

    @JvmStatic
    @BindingAdapter("textKamiNoKuKana", "kimariji")
    fun setTextKamiNoKuKana(view: TextView,
                            kamiNoKu: String?,
                            kimariji: Int?) {
        if (kamiNoKu == null || kimariji == null) {
            return
        }

        var finallyKimariji = 0
        for (i in 0 until kamiNoKu.length - 1) {
            if (kamiNoKu.substring(i, i + 1) == Constants.SPACE) {
                finallyKimariji++
            } else {
                if (kimariji < i) {
                    break
                }
            }
            finallyKimariji++
        }
        val ssb = SpannableStringBuilder().append(kamiNoKu)
        ssb.setSpan(ForegroundColorSpan(Color.RED), 0, finallyKimariji - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.text = ssb
    }
}