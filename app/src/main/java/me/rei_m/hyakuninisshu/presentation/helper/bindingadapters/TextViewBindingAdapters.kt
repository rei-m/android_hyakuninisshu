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
import android.graphics.Color
import android.support.annotation.DimenRes
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.widget.TextView
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.ext.diffString
import me.rei_m.hyakuninisshu.ext.toKarutaNoStr
import me.rei_m.hyakuninisshu.ext.toKimarijiStr
import me.rei_m.hyakuninisshu.presentation.widget.view.VerticalSingleLineTextView
import me.rei_m.hyakuninisshu.util.SPACE
import java.util.Date
import java.util.Locale

@BindingAdapter("verticalText")
fun setVerticalText(
    view: VerticalSingleLineTextView,
    text: String?
) {
    view.drawText(text)
}

@BindingAdapter("verticalTextSize")
fun setVerticalTextSize(
    view: VerticalSingleLineTextView,
    @DimenRes dimenId: Int
) {
    view.setTextSize(dimenId)
}

@BindingAdapter("verticalTextSizeByPx")
fun setVerticalTextSizeByPx(
    view: VerticalSingleLineTextView,
    textSize: Int?
) {
    textSize ?: return
    view.setTextSizeByPx(textSize)
}

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

@BindingAdapter("textSizeByPx")
fun setTextSizeByPx(
    view: TextView,
    textSize: Int?
) {
    textSize ?: return
    view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
}

@BindingAdapter("karutaNo")
fun setKarutaNo(view: TextView, karutaNo: Int?) {
    karutaNo ?: return
    val context = view.context.applicationContext
    view.text = karutaNo.toKarutaNoStr(context)
}

@BindingAdapter("karutaNo", "kimariji")
fun setKarutaNoAndKimariji(
    view: TextView,
    karutaNo: Int?,
    kimariji: Int?
) {
    if (karutaNo == null || kimariji == null || kimariji <= 0) {
        return
    }

    val context = view.context.applicationContext
    val text = "${karutaNo.toKarutaNoStr(context)} / ${kimariji.toKimarijiStr(context)}"
    view.text = text
}

@BindingAdapter("karutaNo", "creator")
fun setKarutaNoAndCreator(
    view: TextView,
    karutaNo: Int?,
    creator: String?
) {

    if (karutaNo == null || creator == null) {
        return
    }

    val context = view.context.applicationContext
    val text = "${karutaNo.toKarutaNoStr(context)} / $creator"
    view.text = text
}

@BindingAdapter("textKamiNoKuKana", "kimariji")
fun setTextKamiNoKuKana(
    view: TextView,
    kamiNoKu: String?,
    kimariji: Int?
) {
    if (kamiNoKu == null || kimariji == null) {
        return
    }

    var finallyKimariji = 0
    for (i in 0 until kamiNoKu.length - 1) {
        if (kamiNoKu.substring(i, i + 1) == SPACE) {
            finallyKimariji++
        } else {
            if (kimariji < i) {
                break
            }
        }
        finallyKimariji++
    }
    val ssb = SpannableStringBuilder().append(kamiNoKu)
    ssb.setSpan(
        ForegroundColorSpan(Color.RED),
        0,
        finallyKimariji - 1,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    view.text = ssb
}

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

@BindingAdapter("examTime")
fun setExamTime(view: TextView, value: Date?) {
    value ?: return
    view.text = value.diffString(view.context.applicationContext, Date())
}
