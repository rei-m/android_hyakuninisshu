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
