package me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.rei_m.hyakuninisshu.databinding.AdapterItemMaterialKarutaBinding;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialViewModel;

public class MaterialKarutaListAdapter extends RecyclerView.Adapter<MaterialKarutaListAdapter.ItemViewHolder> {

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public final AdapterItemMaterialKarutaBinding binding;

        ItemViewHolder(@NonNull AdapterItemMaterialKarutaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private List<MaterialViewModel.KarutaViewModel> karutaViewModelList = new ArrayList<>();

    private OnRecyclerViewInteractionListener listener;

    public MaterialKarutaListAdapter() {

    }

    public void setKarutaViewModelList(@NonNull List<MaterialViewModel.KarutaViewModel> karutaViewModelList) {
        this.karutaViewModelList = karutaViewModelList;
    }

    public void setListener(@Nullable OnRecyclerViewInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterItemMaterialKarutaBinding binding = AdapterItemMaterialKarutaBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        MaterialViewModel.KarutaViewModel viewModel = karutaViewModelList.get(position);
        holder.binding.setViewModel(viewModel);
        holder.binding.setListener(listener);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return karutaViewModelList.size();
    }

    public interface OnRecyclerViewInteractionListener {
        void onItemClicked(int karutaNo);
    }
}
