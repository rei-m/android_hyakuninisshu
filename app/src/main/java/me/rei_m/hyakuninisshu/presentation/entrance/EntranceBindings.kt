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

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.databinding.InverseBindingListener
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.presentation.enums.*
import me.rei_m.hyakuninisshu.presentation.widget.adapter.SpinnerAdapter

object EntranceBindings {
    @JvmStatic
    @BindingAdapter("materials")
    fun setMaterial(view: RecyclerView, karutaList: List<Karuta>?) {
        karutaList ?: return
        with(view.adapter as MaterialListAdapter) {
            replaceData(karutaList)
        }
    }

    @JvmStatic
    @BindingAdapter("spinnerAdapter")
    fun setSpinnerAdapter(spinner: Spinner, spinnerAdapter: SpinnerAdapter) {
        spinner.adapter = spinnerAdapter
    }

    @JvmStatic
    @BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
    fun bindTrainingRangeData(spinner: Spinner,
                              newSelectedValue: TrainingRangeFrom,
                              newTextAttrChanged: InverseBindingListener) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    fun captureSelectedTrainingRangeFrom(spinner: Spinner): TrainingRangeFrom {
        return spinner.selectedItem as TrainingRangeFrom
    }

    @JvmStatic
    @BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
    fun bindTrainingRangeData(spinner: Spinner,
                              newSelectedValue: TrainingRangeTo,
                              newTextAttrChanged: InverseBindingListener) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    fun captureSelectedTrainingRangeTo(spinner: Spinner): TrainingRangeTo {
        return spinner.selectedItem as TrainingRangeTo
    }

    @JvmStatic
    @BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
    fun bindKimariji(spinner: Spinner,
                     newSelectedValue: KimarijiFilter,
                     newTextAttrChanged: InverseBindingListener) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    fun captureSelectedKimariji(spinner: Spinner): KimarijiFilter {
        return spinner.selectedItem as KimarijiFilter
    }

    @JvmStatic
    @BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
    fun bindKarutaStyle(spinner: Spinner,
                        newSelectedValue: KarutaStyleFilter,
                        newTextAttrChanged: InverseBindingListener) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    fun captureSelectedKarutaStyle(spinner: Spinner): KarutaStyleFilter {
        return spinner.selectedItem as KarutaStyleFilter
    }

    @JvmStatic
    @BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
    fun bindColor(spinner: Spinner,
                  newSelectedValue: ColorFilter,
                  newTextAttrChanged: InverseBindingListener) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    fun captureSelectedColor(spinner: Spinner): ColorFilter {
        return spinner.selectedItem as ColorFilter
    }

    private fun bindSpinner(spinner: Spinner,
                            newSelectedValue: SpinnerItem?,
                            newTextAttrChanged: InverseBindingListener) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                newTextAttrChanged.onChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        if (newSelectedValue != null) {
            val pos = (spinner.adapter as SpinnerAdapter).getPosition(newSelectedValue)
            spinner.setSelection(pos, true)
        }
    }
}