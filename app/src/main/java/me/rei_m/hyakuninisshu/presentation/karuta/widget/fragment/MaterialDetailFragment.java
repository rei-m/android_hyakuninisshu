package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialDetailBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialDetailViewModel;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component.MaterialDetailFragmentComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.MaterialDetailFragmentModule;

public class MaterialDetailFragment extends BaseFragment implements MaterialDetailContact.View {

    public static final String TAG = "MaterialDetailFragment";

    private static final String ARG_KARUTA_NO = "karutaNo";

    public static MaterialDetailFragment newInstance(int karutaNo) {
        MaterialDetailFragment fragment = new MaterialDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_KARUTA_NO, karutaNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    MaterialDetailContact.Actions presenter;

    private FragmentMaterialDetailBinding binding;

    private int karutaNo;

    public MaterialDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: エラーチェック.
            karutaNo = getArguments().getInt(ARG_KARUTA_NO);
        }
        presenter.onCreate(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMaterialDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(karutaNo);
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
                .plus(new MaterialDetailFragmentModule(getContext())).inject(this);
    }

    @Override
    public void initialize(MaterialDetailViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    public interface Injector {
        MaterialDetailFragmentComponent plus(MaterialDetailFragmentModule fragmentModule);
    }
}
