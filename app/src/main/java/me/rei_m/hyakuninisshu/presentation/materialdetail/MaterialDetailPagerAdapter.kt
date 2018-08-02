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

package me.rei_m.hyakuninisshu.presentation.materialdetail

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta

class MaterialDetailPagerAdapter(
        fm: FragmentManager,
        private var karutaList: List<Karuta> = listOf()
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) = MaterialDetailFragment.newInstance(karutaList[position].identifier())

    override fun getCount() = karutaList.size

    fun replaceData(karutaList: List<Karuta>) {
        this.karutaList = karutaList
        notifyDataSetChanged()
    }
}
