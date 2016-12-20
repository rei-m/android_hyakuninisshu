package me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.AdapterItemMaterialKarutaBinding;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialViewModel;

public class MaterialKarutaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        public final AdapterItemMaterialKarutaBinding binding;

        ItemViewHolder(@NonNull AdapterItemMaterialKarutaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private enum ItemViewType {
        ITEM,
        FOOTER;

        static ItemViewType forId(int id) {
            for (ItemViewType value : values()) {
                if (value.ordinal() == id) {
                    return value;
                }
            }
            throw new AssertionError("no enum found for the id. you forgot to implement?");
        }
    }

    private static final int FOOTER_COUNT = 1;

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
    public int getItemViewType(int position) {
        return (position < karutaViewModelList.size()) ? ItemViewType.ITEM.ordinal() : ItemViewType.FOOTER.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (ItemViewType.forId(viewType)) {
            case ITEM:
                AdapterItemMaterialKarutaBinding binding = AdapterItemMaterialKarutaBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,
                        false);
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
            MaterialViewModel.KarutaViewModel viewModel = karutaViewModelList.get(position);
            itemViewHolder.binding.setViewModel(viewModel);
            itemViewHolder.binding.setListener(listener);
            itemViewHolder.binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return karutaViewModelList.size() + FOOTER_COUNT;
    }

    public interface OnRecyclerViewInteractionListener {
        void onItemClicked(int karutaNo);
    }
}
