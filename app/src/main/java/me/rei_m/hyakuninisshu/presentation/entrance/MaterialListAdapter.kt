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

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.databinding.AdapterItemMaterialKarutaBinding
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta

class MaterialListAdapter(
        private var karutaList: List<Karuta>,
        private var viewModel: MaterialListViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int) = if (position < karutaList.size)
        ItemViewType.ITEM.ordinal
    else
        ItemViewType.FOOTER.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (ItemViewType.forId(viewType)) {
            ItemViewType.ITEM -> {
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
            else -> {
                val context = parent.context
                val view = View(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            context.resources.getDimensionPixelOffset(R.dimen.height_ad_banner)
                    )
                }
                return object : RecyclerView.ViewHolder(view) {
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            with(holder.binding) {
                this.karuta = karutaList[position]
                this.position = position
                executePendingBindings()
            }
        }
    }

    override fun getItemCount() = karutaList.size + FOOTER_COUNT

    fun replaceData(karutaList: List<Karuta>) {
        this.karutaList = karutaList
        notifyDataSetChanged()
    }

    interface OnItemInteractionListener {
        fun onItemClicked(position: Int)
    }

    private enum class ItemViewType {
        ITEM,
        FOOTER;

        companion object {
            fun forId(id: Int): ItemViewType = values()[id]
        }
    }

    private class ItemViewHolder(val binding: AdapterItemMaterialKarutaBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val FOOTER_COUNT = 1
    }
}
