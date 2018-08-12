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

package me.rei_m.hyakuninisshu.presentation.entrance

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.databinding.AdapterItemMaterialKarutaBinding
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.ext.ContextExt

class MaterialListAdapter(
        context: Context,
        private var karutaList: List<Karuta>,
        private var viewModel: MaterialListViewModel
) : RecyclerView.Adapter<MaterialListAdapter.ItemViewHolder>(), ContextExt {

    private val itemPaddingBottom = context.resources.getDimensionPixelOffset(R.dimen.padding_s)

    private val lastItemPaddingBottom = context.adHeight + itemPaddingBottom

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = AdapterItemMaterialKarutaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        binding.listener = object : OnItemInteractionListener {
            override fun onItemClicked(position: Int) {
                viewModel.onClickItem(position)
            }
        }
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder.binding) {
            val paddingBottom = if (position == karutaList.lastIndex) {
                lastItemPaddingBottom
            } else {
                itemPaddingBottom
            }
            holder.binding.layoutRoot.setPadding(
                    holder.binding.layoutRoot.paddingLeft,
                    holder.binding.layoutRoot.paddingTop,
                    holder.binding.layoutRoot.paddingRight,
                    paddingBottom
            )
            this.karuta = karutaList[position]
            this.position = position
            executePendingBindings()
        }
    }

    override fun getItemCount() = karutaList.size

    fun replaceData(karutaList: List<Karuta>) {
        this.karutaList = karutaList
        notifyDataSetChanged()
    }

    interface OnItemInteractionListener {
        fun onItemClicked(position: Int)
    }

    class ItemViewHolder(val binding: AdapterItemMaterialKarutaBinding) : RecyclerView.ViewHolder(binding.root)
}
