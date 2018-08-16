package me.rei_m.hyakuninisshu.presentation.helper.bindingadapters

import android.databinding.BindingAdapter
import android.support.v4.view.ViewPager
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.presentation.materialdetail.MaterialDetailPagerAdapter

@BindingAdapter("materials", "initialPosition")
fun setMaterial(pager: ViewPager, karutaList: List<Karuta>?, initialPosition: Int?) {
    karutaList ?: return
    initialPosition ?: return
    val adapter = pager.adapter as MaterialDetailPagerAdapter
    val isFirstSetValue = adapter.count == 0
    adapter.replaceData(karutaList)
    if (isFirstSetValue) {
        pager.setCurrentItem(initialPosition, false)
    }
}
