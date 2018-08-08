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

package me.rei_m.hyakuninisshu.presentation.examhistory

import android.support.constraint.ConstraintSet
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.databinding.AdapterItemKarutaExamBinding
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam

class KarutaExamListAdapter(
        private var karutaExamList: List<KarutaExam>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int) = if (position < karutaExamList.size)
        ItemViewType.ITEM.ordinal
    else
        ItemViewType.FOOTER.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (ItemViewType.forId(viewType)) {
            ItemViewType.ITEM -> {
                val binding = AdapterItemKarutaExamBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
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
            val context = holder.binding.layoutRoot.context
            val constraintSet = ConstraintSet()
            constraintSet.clone(holder.binding.layoutRoot)
            constraintSet.setMargin(R.id.karuta_creator, ConstraintSet.BOTTOM, context.resources.getDimensionPixelOffset(R.dimen.height_ad_banner))
            holder.binding.layoutRoot.setConstraintSet(constraintSet)
            with(holder.binding) {

//                marginBottom
//                this.karuta = karutaList[position]
//                this.position = position
//                executePendingBindings()
            }
        }
    }

    override fun getItemCount() = karutaExamList.size + FOOTER_COUNT

    fun replaceData(karutaExamList: List<KarutaExam>) {
        this.karutaExamList = karutaExamList
        notifyDataSetChanged()
    }

    private enum class ItemViewType {
        ITEM,
        FOOTER;

        companion object {
            fun forId(id: Int): ItemViewType = values()[id]
        }
    }

    private class ItemViewHolder(val binding: AdapterItemKarutaExamBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val FOOTER_COUNT = 1
    }
}
