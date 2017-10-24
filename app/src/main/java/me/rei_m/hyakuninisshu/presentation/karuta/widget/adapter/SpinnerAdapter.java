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

package me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter;

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

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.SpinnerItem;

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
            return new SpinnerAdapter(context, R.layout.item_spinner, R.layout.item_spinner_dropdown, values);
        } else {
            return new SpinnerAdapter(context, R.layout.item_spinner, R.layout.item_spinner_dropdown, objects);
        }
    }

    public static SpinnerAdapter newInstance(Context context, List<SpinnerItem> objects, boolean withNullValue) {
        if (withNullValue) {
            List<SpinnerItem> values = new ArrayList<>();
            values.add(null);
            values.addAll(objects);
            return new SpinnerAdapter(context, R.layout.item_spinner, R.layout.item_spinner_dropdown, values);
        } else {
            return new SpinnerAdapter(context, R.layout.item_spinner, R.layout.item_spinner_dropdown, objects);
        }
    }

    private SpinnerAdapter(Context context, @LayoutRes int resource, @LayoutRes int dropDownResource, SpinnerItem[] objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.dropDownResource = dropDownResource;
    }

    private SpinnerAdapter(Context context, @LayoutRes int resource, @LayoutRes int dropDownResource, List<SpinnerItem> objects) {
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
        TextView view = convertView.findViewById(R.id.text_spinner_item);
        SpinnerItem item = getItem(position);
        if (item != null) {
            view.setText(item.label(getContext().getResources()));
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
        TextView view = convertView.findViewById(R.id.text_spinner_item);
        SpinnerItem item = getItem(position);
        if (item != null) {
            view.setText(item.label(getContext().getResources()));
        } else {
            view.setText("");
        }
        return view;
    }
}
