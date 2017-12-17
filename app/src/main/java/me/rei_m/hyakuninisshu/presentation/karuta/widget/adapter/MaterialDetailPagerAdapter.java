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

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialDetailFragment;

public class MaterialDetailPagerAdapter extends FragmentStatePagerAdapter {

    private final ObservableArrayList<Karuta> karutaList;

    private final ObservableList.OnListChangedCallback<ObservableList<Karuta>> listChangedCallback = new ObservableList.OnListChangedCallback<ObservableList<Karuta>>() {
        @Override
        public void onChanged(ObservableList<Karuta> karutas) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList<Karuta> karutas, int i, int i1) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(ObservableList<Karuta> karutas, int i, int i1) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeMoved(ObservableList<Karuta> karutas, int i, int i1, int i2) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(ObservableList<Karuta> karutas, int i, int i1) {
            notifyDataSetChanged();
        }
    };

    public MaterialDetailPagerAdapter(@NonNull FragmentManager fm,
                                      @NonNull ObservableArrayList<Karuta> karutaList) {
        super(fm);
        this.karutaList = karutaList;
        this.karutaList.addOnListChangedCallback(listChangedCallback);
    }

    @Override
    public Fragment getItem(int position) {
        return MaterialDetailFragment.newInstance(karutaList.get(position).identifier());
    }

    @Override
    public int getCount() {
        return karutaList.size();
    }

    public void releaseCallback() {
        karutaList.removeOnListChangedCallback(listChangedCallback);
    }
}
