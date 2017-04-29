package me.rei_m.hyakuninisshu.presentation.support.widget.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentSupportBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.support.widget.fragment.component.SupportFragmentComponent;
import me.rei_m.hyakuninisshu.presentation.support.widget.fragment.module.SupportFragmentModule;
import me.rei_m.hyakuninisshu.viewmodel.support.widget.fragment.SupportFragmentViewModel;

public class SupportFragment extends BaseFragment {

    public static final String TAG = "SupportFragment";

    public static SupportFragment newInstance() {
        return new SupportFragment();
    }

    @Inject
    SupportFragmentViewModel viewModel;

    private FragmentSupportBinding binding;

    private CompositeDisposable disposable;

    public SupportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSupportBinding.inflate(inflater, container, false);
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
        disposable = new CompositeDisposable();
        disposable.add(viewModel.onClickLicenseEvent.subscribe(v -> {
            FragmentManager manager = getFragmentManager();
            if (manager.findFragmentByTag(LicenceDialogFragment.TAG) == null) {
                LicenceDialogFragment dialog = LicenceDialogFragment.newInstance();
                dialog.show(manager, LicenceDialogFragment.TAG);
            }
        }));
        viewModel.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
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
    @SuppressWarnings("unchecked")
    protected void setupFragmentComponent() {
        ((HasComponent<Injector>) getActivity()).getComponent()
                .plus(new SupportFragmentModule(getContext())).inject(this);
    }

    public interface Injector {
        SupportFragmentComponent plus(SupportFragmentModule fragmentModule);
    }
}
