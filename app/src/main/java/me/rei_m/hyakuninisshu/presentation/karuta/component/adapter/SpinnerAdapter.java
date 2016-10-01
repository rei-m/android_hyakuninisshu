package me.rei_m.hyakuninisshu.presentation.karuta.component.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.SpinnerItem;

public class SpinnerAdapter extends ArrayAdapter<SpinnerItem> {

    @LayoutRes
    private int resource;

    @LayoutRes
    private int dropDownResource;

    public static SpinnerAdapter newInstance(Context context, SpinnerItem[] objects, boolean withNullValue) {
        if (withNullValue) {
            List<SpinnerItem> values = new ArrayList<>();
            values.add(null);
            values.addAll(Arrays.asList(objects));
            return new SpinnerAdapter(context, android.R.layout.simple_list_item_1, android.R.layout.simple_spinner_dropdown_item, values);
        } else {
            return new SpinnerAdapter(context, android.R.layout.simple_list_item_1, android.R.layout.simple_spinner_dropdown_item, objects);
        }
    }

    public static SpinnerAdapter newInstance(Context context, List<SpinnerItem> objects, boolean withNullValue) {
        if (withNullValue) {
            List<SpinnerItem> values = new ArrayList<>();
            values.add(null);
            values.addAll(objects);
            return new SpinnerAdapter(context, android.R.layout.simple_list_item_1, android.R.layout.simple_spinner_dropdown_item, values);
        } else {
            return new SpinnerAdapter(context, android.R.layout.simple_list_item_1, android.R.layout.simple_spinner_dropdown_item, objects);
        }
    }

    public SpinnerAdapter(Context context, @LayoutRes int resource, @LayoutRes int dropDownResource, SpinnerItem[] objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.dropDownResource = dropDownResource;
    }

    public SpinnerAdapter(Context context, @LayoutRes int resource, @LayoutRes int dropDownResource, List<SpinnerItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.dropDownResource = dropDownResource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
        }
        TextView view = (TextView) convertView.findViewById(android.R.id.text1);
        SpinnerItem item = getItem(position);
        if (item != null) {
            view.setText(item.getLabel(getContext().getResources()));
        } else {
            view.setText("");
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(dropDownResource, null);
        }
        TextView view = (TextView) convertView.findViewById(android.R.id.text1);
        SpinnerItem item = getItem(position);
        if (item != null) {
            view.setText(item.getLabel(getContext().getResources()));
        } else {
            view.setText("");
        }
        return view;
    }
}
