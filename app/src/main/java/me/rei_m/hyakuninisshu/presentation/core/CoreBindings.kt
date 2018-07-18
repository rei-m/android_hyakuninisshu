package me.rei_m.hyakuninisshu.presentation.core

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.presentation.helper.GlideApp
import me.rei_m.hyakuninisshu.presentation.helper.KarutaDisplayHelper

object CoreBindings {
    @JvmStatic
    @BindingAdapter("app:textForQuiz", "app:textPosition")
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
    @BindingAdapter("app:isCorrect")
    fun setIsCorrect(imageView: ImageView,
                     isCorrect: Boolean?) {
        isCorrect ?: let { return }
        val resId = if (isCorrect) R.drawable.check_correct else R.drawable.check_incorrect
        GlideApp.with(imageView.context).load(resId).dontAnimate().into(imageView)
    }

    @JvmStatic
    @BindingAdapter("app:karutaNo", "app:kimariji")
    fun setKarutaNoAndKimariji(view: TextView,
                               karutaNo: Int?,
                               kimariji: Int?) {
        karutaNo ?: let { return }
        kimariji ?: let { return }

        if (kimariji <= 0) {
            return
        }

        val context = view.context.applicationContext
        val text = KarutaDisplayHelper.convertNumberToString(context, karutaNo) + " / " +
                KarutaDisplayHelper.convertKimarijiToString(context, kimariji)
        view.text = text
    }
}
