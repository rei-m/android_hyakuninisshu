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
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.AdapterItemMaterialKarutaBinding;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.adapter.KarutaListItemViewModel;

public class MaterialKarutaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int FOOTER_COUNT = 1;

    private final ObservableArrayList<Karuta> karutaList;

    private OnItemInteractionListener listener;

    private final Injector injector;

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

    public MaterialKarutaListAdapter(@NonNull ObservableArrayList<Karuta> karutaList,
                                     @NonNull OnItemInteractionListener listener,
                                     @NonNull Injector injector) {
        this.karutaList = karutaList;
        this.listener = listener;
        this.injector = injector;

        this.karutaList.addOnListChangedCallback(listChangedCallback);
    }

    @Override
    public int getItemViewType(int position) {
        return (position < karutaList.size()) ? ItemViewType.ITEM.ordinal() : ItemViewType.FOOTER.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (ItemViewType.forId(viewType)) {
            case ITEM:
                AdapterItemMaterialKarutaBinding binding = AdapterItemMaterialKarutaBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,
                        false);
                binding.setViewModel(injector.karutaListItemViewModel());
                return new ItemViewHolder(binding);
            default:
                Context context = parent.getContext();
                View view = new View(context);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        context.getResources().getDimensionPixelOffset(R.dimen.height_ad_banner));
                view.setLayoutParams(layoutParams);
                return new RecyclerView.ViewHolder(view) {
                };
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.binding.getViewModel().setKaruta(karutaList.get(position));
            itemViewHolder.binding.getViewModel().setPosition(position);
            itemViewHolder.binding.getViewModel().setListener(listener);
            itemViewHolder.binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return karutaList.size() + FOOTER_COUNT;
    }

    public void releaseCallback() {
        listener = null;
        karutaList.removeOnListChangedCallback(listChangedCallback);
    }

    private enum ItemViewType {
        ITEM,
        FOOTER;

        static ItemViewType forId(int id) {
            return values()[id];
        }
    }

    public interface Injector {
        KarutaListItemViewModel karutaListItemViewModel();
    }

    public interface OnItemInteractionListener {
        void onItemClicked(int position);
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        public final AdapterItemMaterialKarutaBinding binding;

        ItemViewHolder(@NonNull AdapterItemMaterialKarutaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
