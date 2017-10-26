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

package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialEditBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.dialog.ConfirmMaterialEditDialogFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialEditFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.MaterialEditFragmentViewModelModule;

public class MaterialEditFragment extends DaggerFragment implements ConfirmMaterialEditDialogFragment.OnDialogInteractionListener {

    public static final String TAG = MaterialEditFragment.class.getSimpleName();

    private static final String ARG_KARUTA_ID = "karutaId";

    public static MaterialEditFragment newInstance(@NonNull KarutaIdentifier karutaId) {
        MaterialEditFragment fragment = new MaterialEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_KARUTA_ID, karutaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    MaterialEditFragmentViewModel viewModel;

    private FragmentMaterialEditBinding binding;

    private OnFragmentInteractionListener listener;

    private CompositeDisposable disposable;

    private KarutaIdentifier karutaId;

    public MaterialEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            karutaId = getArguments().getParcelable(ARG_KARUTA_ID);
        }

        if (karutaId == null) {
            if (listener != null) {
                listener.onReceiveIllegalArguments();
            }
            return;
        }

        viewModel.onCreate(karutaId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMaterialEditBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        disposable = new CompositeDisposable();
        disposable.addAll(viewModel.onClickEditEvent.subscribe(v -> {
                    FragmentManager manger = getFragmentManager();
                    if (manger == null) {
                        return;
                    }
                    ConfirmMaterialEditDialogFragment fragment = ConfirmMaterialEditDialogFragment.newInstance(
                            viewModel.firstPhraseKanji.get(),
                            viewModel.firstPhraseKana.get(),
                            viewModel.secondPhraseKanji.get(),
                            viewModel.secondPhraseKana.get(),
                            viewModel.thirdPhraseKanji.get(),
                            viewModel.thirdPhraseKana.get(),
                            viewModel.fourthPhraseKanji.get(),
                            viewModel.fourthPhraseKana.get(),
                            viewModel.fifthPhraseKanji.get(),
                            viewModel.fifthPhraseKana.get()
                    );
                    fragment.setTargetFragment(this, 0);
                    fragment.show(manger, ConfirmMaterialEditDialogFragment.TAG);
                }), viewModel.onErrorEditEvent.subscribe(v -> {
                    if (getView() != null) {
                        Snackbar.make(getView(), getString(R.string.text_message_edit_error), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null)
                                .show();
                    }
                }),
                viewModel.onUpdateMaterialEvent.subscribe(v -> {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                }));
        viewModel.onStart();
    }

    @Override
    public void onStop() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        viewModel.onStop();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    public void onPause() {
        viewModel.onPause();
        super.onPause();
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
        viewModel = null;
        listener = null;
        super.onDetach();
    }

    @Override
    public void onConfirmMaterialEditDialogPositiveClick() {
        viewModel.onClickDialogPositive();
    }

    @Override
    public void onConfirmMaterialEditDialogNegativeClick() {

    }

    public interface OnFragmentInteractionListener {
        void onReceiveIllegalArguments();
    }

    @dagger.Module
    public abstract class Module {
        @SuppressWarnings("unused")
        @ForFragment
        @ContributesAndroidInjector(modules = MaterialEditFragmentViewModelModule.class)
        abstract MaterialEditFragment contributeInjector();
    }
}
