package me.rei_m.hyakuninisshu.presentation.utilitty;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import me.rei_m.hyakuninisshu.presentation.karuta.component.adapter.SpinnerAdapter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;

public class SpinnerBindingUtil {

    private SpinnerBindingUtil() {
    }

    @BindingAdapter({"spinnerAdapter"})
    public static void setSpinnerAdapter(Spinner spinner, SpinnerAdapter spinnerAdapter) {
        spinner.setAdapter(spinnerAdapter);
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindTrainingRangeData(Spinner spinner,
                                             TrainingRange newSelectedValue,
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

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static TrainingRange captureSelectedTrainingRange(Spinner spinner) {
        return (TrainingRange) spinner.getSelectedItem();
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindKimariji(Spinner spinner,
                                    Kimariji newSelectedValue,
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

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static Kimariji captureSelectedKimariji(Spinner spinner) {
        return (Kimariji) spinner.getSelectedItem();
    }
}
