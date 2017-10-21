package me.rei_m.hyakuninisshu.presentation.utility;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.SpinnerItem;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeTo;
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
