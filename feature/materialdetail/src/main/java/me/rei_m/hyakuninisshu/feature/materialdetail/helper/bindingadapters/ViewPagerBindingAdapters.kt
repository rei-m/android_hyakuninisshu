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
package me.rei_m.hyakuninisshu.feature.materialdetail.helper.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.feature.materialdetail.ui.MaterialDetailPagerAdapter

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
