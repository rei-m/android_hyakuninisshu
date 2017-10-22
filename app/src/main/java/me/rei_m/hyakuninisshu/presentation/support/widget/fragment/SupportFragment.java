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

package me.rei_m.hyakuninisshu.presentation.support.widget.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.databinding.FragmentSupportBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.support.widget.fragment.SupportFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.support.widget.fragment.di.SupportFragmentViewModelModule;

public class SupportFragment extends DaggerFragment {

    public static final String TAG = SupportFragment.class.getSimpleName();

    public static SupportFragment newInstance() {
        return new SupportFragment();
    }

    @dagger.Module
    public abstract class Module {
        @ForFragment
        @ContributesAndroidInjector(modules = SupportFragmentViewModelModule.class)
        abstract SupportFragment contributeInjector();
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
    public void onDetach() {
        super.onDetach();
        viewModel = null;
    }
}
