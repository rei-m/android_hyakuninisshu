package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialBinding;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialViewModel;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.MaterialKarutaListAdapter;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component.MaterialFragmentComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.MaterialFragmentModule;

public class MaterialFragment extends BaseFragment implements MaterialContact.View {

    public static final String TAG = "MaterialFragment";

    public static MaterialFragment newInstance() {
        return new MaterialFragment();
    }

    @Inject
    ActivityNavigator navigator;

    @Inject
    MaterialContact.Actions presenter;

    private FragmentMaterialBinding binding;

    public MaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        navigator = null;
        presenter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMaterialBinding.inflate(inflater, container, false);
        binding.setPresenter(presenter);
        MaterialKarutaListAdapter adapter = new MaterialKarutaListAdapter();
        adapter.setListener(presenter);
        binding.recyclerKarutaList.setAdapter(adapter);
        binding.recyclerKarutaList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MaterialKarutaListAdapter adapter = (MaterialKarutaListAdapter) binding.recyclerKarutaList.getAdapter();
        adapter.setListener(null);
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected void setupFragmentComponent() {
        ((HasComponent<Injector>) getActivity()).getComponent()
                .plus(new MaterialFragmentModule(getContext())).inject(this);
    }

    @Override
    public void initialize(MaterialViewModel viewModel) {
        MaterialKarutaListAdapter adapter = (MaterialKarutaListAdapter) binding.recyclerKarutaList.getAdapter();
        adapter.setKarutaViewModelList(viewModel.karutaList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToMaterialDetail(int karutaNo) {
        navigator.navigateToMaterialDetail(getActivity(), karutaNo);
    }

    public interface Injector {
        MaterialFragmentComponent plus(MaterialFragmentModule fragmentModule);
    }
}
