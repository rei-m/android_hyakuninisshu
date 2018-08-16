package me.rei_m.hyakuninisshu.presentation.helper.bindingadapters

import android.databinding.BindingAdapter
import android.view.View

@BindingAdapter("visibleOrGone")
fun setVisibleOrGone(view: View, visible: Boolean?) {
    if (visible == true) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("visibleOrInvisible")
fun setVisibleOrInvisible(view: View, visible: Boolean?) {
    if (visible == true) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}
