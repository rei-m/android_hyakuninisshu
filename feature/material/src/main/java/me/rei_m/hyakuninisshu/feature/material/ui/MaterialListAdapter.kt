/*
 * Copyright (c) 2025. Rei Matsushita
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

package me.rei_m.hyakuninisshu.feature.material.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.adHeight
import me.rei_m.hyakuninisshu.feature.material.R
import me.rei_m.hyakuninisshu.feature.material.databinding.AdapterItemMaterialKarutaBinding
import me.rei_m.hyakuninisshu.state.material.model.Material

class MaterialListAdapter(
    context: Context,
    private var materialList: List<Material>,
    private val listener: OnItemInteractionListener,
) : RecyclerView.Adapter<MaterialListAdapter.ItemViewHolder>() {
    private val itemPaddingBottom =
        context.resources.getDimensionPixelOffset(
            me.rei_m.hyakuninisshu.feature.corecomponent.R.dimen.spacing_1,
        )

    private val lastItemPaddingBottom = context.adHeight + itemPaddingBottom

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemViewHolder {
        val binding =
            AdapterItemMaterialKarutaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        binding.listener = listener

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int,
    ) {
        with(holder.binding) {
            val paddingBottom =
                if (position == materialList.lastIndex) {
                    lastItemPaddingBottom
                } else {
                    itemPaddingBottom
                }
            holder.binding.rootAdapterItemMaterialKaruta.setPadding(
                holder.binding.rootAdapterItemMaterialKaruta.paddingLeft,
                holder.binding.rootAdapterItemMaterialKaruta.paddingTop,
                holder.binding.rootAdapterItemMaterialKaruta.paddingRight,
                paddingBottom,
            )
            this.material = materialList[position]
            this.position = position
            executePendingBindings()
        }
    }

    override fun getItemCount() = materialList.size

    fun replaceData(materialList: List<Material>) {
        this.materialList = materialList
        notifyDataSetChanged()
    }

    interface OnItemInteractionListener {
        fun onItemClicked(position: Int)
    }

    inner class ItemViewHolder(
        val binding: AdapterItemMaterialKarutaBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}
