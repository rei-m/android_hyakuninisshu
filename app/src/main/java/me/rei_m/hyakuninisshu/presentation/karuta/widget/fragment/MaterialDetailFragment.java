package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialDetailBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component.MaterialDetailFragmentComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.MaterialDetailFragmentModule;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialDetailFragmentViewModel;

public class MaterialDetailFragment extends BaseFragment {

    public static final String TAG = "MaterialDetailFragment";

    private static final String ARG_KARUTA_NO = "karutaNo";

    private static final int INVALID_KARUTA_NO = -1;

    public static MaterialDetailFragment newInstance(int karutaNo) {
        MaterialDetailFragment fragment = new MaterialDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_KARUTA_NO, karutaNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    MaterialDetailFragmentViewModel viewModel;

    private FragmentMaterialDetailBinding binding;

    private OnFragmentInteractionListener listener;

    private int karutaNo = INVALID_KARUTA_NO;

    public MaterialDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            karutaNo = getArguments().getInt(ARG_KARUTA_NO, INVALID_KARUTA_NO);
        }

        if (karutaNo == INVALID_KARUTA_NO) {
            if (listener != null) {
                listener.onReceiveIllegalArguments();
            }
            return;
        }
        viewModel.onCreate(karutaNo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMaterialDetailBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void setupFragmentComponent() {
        ((HasComponent<Injector>) getActivity()).getComponent()
                .plus(new MaterialDetailFragmentModule(getContext())).inject(this);
    }

    public interface Injector {
        MaterialDetailFragmentComponent plus(MaterialDetailFragmentModule fragmentModule);
    }

    public interface OnFragmentInteractionListener {
        void onReceiveIllegalArguments();
    }
}
