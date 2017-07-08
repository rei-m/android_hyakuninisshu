package me.rei_m.hyakuninisshu.presentation.utility;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
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
                                    Kimariji newSelectedValue,
                                    final InverseBindingListener newTextAttrChanged) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged);
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static Kimariji captureSelectedKimariji(Spinner spinner) {
        return (Kimariji) spinner.getSelectedItem();
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindKarutaStyle(Spinner spinner,
                                       KarutaStyle newSelectedValue,
                                       final InverseBindingListener newTextAttrChanged) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged);
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static KarutaStyle captureSelectedKarutaStyle(Spinner spinner) {
        return (KarutaStyle) spinner.getSelectedItem();
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindColor(Spinner spinner,
                                 Color newSelectedValue,
                                 final InverseBindingListener newTextAttrChanged) {
        bindSpinner(spinner, newSelectedValue, newTextAttrChanged);
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static Color captureSelectedColor(Spinner spinner) {
        return (Color) spinner.getSelectedItem();
    }
}
