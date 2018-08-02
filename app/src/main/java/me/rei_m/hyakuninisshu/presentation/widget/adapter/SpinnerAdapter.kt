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

package me.rei_m.hyakuninisshu.presentation.widget.adapter

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.presentation.enums.SpinnerItem

class SpinnerAdapter : ArrayAdapter<SpinnerItem> {

    @LayoutRes
    private val resource: Int

    @LayoutRes
    private val dropDownResource: Int

    private constructor(context: Context, @LayoutRes resource: Int, @LayoutRes dropDownResource: Int, objects: Array<SpinnerItem>) : super(context, resource, objects) {
        this.resource = resource
        this.dropDownResource = dropDownResource
    }

    private constructor(context: Context, @LayoutRes resource: Int, @LayoutRes dropDownResource: Int, objects: List<SpinnerItem>) : super(context, resource, objects) {
        this.resource = resource
        this.dropDownResource = dropDownResource
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val parentView = convertView ?: let {
            LayoutInflater.from(context).inflate(resource, null)
        }
        return parentView.findViewById<TextView>(R.id.text_spinner_item).apply {
            text = getItem(position)?.label(this@SpinnerAdapter.context.resources) ?: let { "" }
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val parentView = convertView ?: let {
            LayoutInflater.from(context).inflate(dropDownResource, null)
        }
        return parentView.findViewById<TextView>(R.id.text_spinner_item).apply {
            text = getItem(position)?.label(this@SpinnerAdapter.context.resources) ?: let { "" }
        }
    }

    companion object {
        fun newInstance(context: Context, objects: Array<SpinnerItem>): SpinnerAdapter {
            return SpinnerAdapter(context, R.layout.item_spinner, R.layout.item_spinner_dropdown, objects)
        }

        fun newInstance(context: Context, objects: List<SpinnerItem>): SpinnerAdapter {
            return SpinnerAdapter(context, R.layout.item_spinner, R.layout.item_spinner_dropdown, objects)
        }
    }
}
