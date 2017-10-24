/*
 * Copyright (c) 2017. Rei Matsushita
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

package me.rei_m.hyakuninisshu.presentation.util;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.SpinnerItem;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeTo;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.SpinnerAdapter;

public class SpinnerBindingAttributeBindingAdapter {

    private SpinnerBindingAttributeBindingAdapter() {
    }

    @BindingAdapter({"spinnerAdapter"})
    public static void setSpinnerAdapter(Spinner spinner, SpinnerAdapter spinnerAdapter) {
        spinner.setAdapter(spinnerAdapter);
    }

    private static void bindSpinner(Spinner spinner,
                                    SpinnerItem newSelectedValue,
                                    final InverseBindingListener newTextAttrChanged) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (newSelectedValue != null) {
            int pos = ((SpinnerAdapter) spinner.getAdapter()).getPosition(newSelectedValue);
            spinner.setSelection(pos, true);
        }
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindTrainingRangeData(Spinner spinner,
                                             TrainingRangeFrom newSelectedValue,
                                             final InverseBindingListener newTextAttrChanged) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged);
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static TrainingRangeFrom captureSelectedTrainingRangeFrom(Spinner spinner) {
        return (TrainingRangeFrom) spinner.getSelectedItem();
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindTrainingRangeData(Spinner spinner,
                                             TrainingRangeTo newSelectedValue,
                                             final InverseBindingListener newTextAttrChanged) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged);
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static TrainingRangeTo captureSelectedTrainingRangeTo(Spinner spinner) {
        return (TrainingRangeTo) spinner.getSelectedItem();
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindKimariji(Spinner spinner,
                                    KimarijiFilter newSelectedValue,
                                    final InverseBindingListener newTextAttrChanged) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged);
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static KimarijiFilter captureSelectedKimariji(Spinner spinner) {
        return (KimarijiFilter) spinner.getSelectedItem();
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindKarutaStyle(Spinner spinner,
                                       KarutaStyleFilter newSelectedValue,
                                       final InverseBindingListener newTextAttrChanged) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged);
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static KarutaStyleFilter captureSelectedKarutaStyle(Spinner spinner) {
        return (KarutaStyleFilter) spinner.getSelectedItem();
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindColor(Spinner spinner,
                                 ColorFilter newSelectedValue,
                                 final InverseBindingListener newTextAttrChanged) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged);
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static ColorFilter captureSelectedColor(Spinner spinner) {
        return (ColorFilter) spinner.getSelectedItem();
    }
}
