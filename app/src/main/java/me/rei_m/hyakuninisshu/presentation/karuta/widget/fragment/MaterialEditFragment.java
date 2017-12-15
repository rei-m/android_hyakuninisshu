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

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.Binds;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerFragment;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialEditBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.dialog.ConfirmMaterialEditDialogFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialEditFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.MaterialEditFragmentViewModelModule;

public class MaterialEditFragment extends DaggerFragment implements ConfirmMaterialEditDialogFragment.OnDialogInteractionListener {

    public static final String TAG = MaterialEditFragment.class.getSimpleName();

    private static final String ARG_KARUTA = "karuta";

    private static final String KEY_FIRST_KANJI = "firstKanji";
    private static final String KEY_FIRST_KANA = "firstKana";
    private static final String KEY_SECOND_KANJI = "secondKanji";
    private static final String KEY_SECOND_KANA = "secondKana";
    private static final String KEY_THIRD_KANJI = "thirdKanji";
    private static final String KEY_THIRD_KANA = "thirdKana";
    private static final String KEY_FOURTH_KANJI = "fourthKanji";
    private static final String KEY_FOURTH_KANA = "fourthKana";
    private static final String KEY_FIFTH_KANJI = "fifthKanji";
    private static final String KEY_FIFTH_KANA = "fifthKana";

    public static MaterialEditFragment newInstance(@NonNull Karuta karuta) {
        MaterialEditFragment fragment = new MaterialEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_KARUTA, karuta);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    MaterialEditFragmentViewModel.Factory viewModelFactory;

    private MaterialEditFragmentViewModel viewModel;

    private FragmentMaterialEditBinding binding;

    private CompositeDisposable disposable;

    public MaterialEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            viewModelFactory.setFirstPhraseKanji(savedInstanceState.getString(KEY_FIRST_KANJI, ""));
            viewModelFactory.setFirstPhraseKana(savedInstanceState.getString(KEY_FIRST_KANA, ""));
            viewModelFactory.setSecondPhraseKanji(savedInstanceState.getString(KEY_SECOND_KANJI, ""));
            viewModelFactory.setSecondPhraseKana(savedInstanceState.getString(KEY_SECOND_KANA, ""));
            viewModelFactory.setThirdPhraseKanji(savedInstanceState.getString(KEY_THIRD_KANJI, ""));
            viewModelFactory.setThirdPhraseKana(savedInstanceState.getString(KEY_THIRD_KANA, ""));
            viewModelFactory.setFourthPhraseKanji(savedInstanceState.getString(KEY_FOURTH_KANJI, ""));
            viewModelFactory.setFourthPhraseKana(savedInstanceState.getString(KEY_FOURTH_KANA, ""));
            viewModelFactory.setFifthPhraseKanji(savedInstanceState.getString(KEY_FIFTH_KANJI, ""));
            viewModelFactory.setFifthPhraseKana(savedInstanceState.getString(KEY_FIFTH_KANA, ""));
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MaterialEditFragmentViewModel.class);
    }

    @Override
    public void onDestroy() {
        viewModel = null;
        super.onDestroy();
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
                    FragmentManager manager = getFragmentManager();
                    if (manager == null) {
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
                    fragment.show(manager, ConfirmMaterialEditDialogFragment.TAG);
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
    }

    @Override
    public void onStop() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onStop();
    }

    @Override
    public void onDetach() {
        viewModelFactory = null;
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_FIRST_KANJI, viewModel.firstPhraseKanji.get());
        outState.putString(KEY_FIRST_KANA, viewModel.firstPhraseKana.get());
        outState.putString(KEY_SECOND_KANJI, viewModel.secondPhraseKanji.get());
        outState.putString(KEY_SECOND_KANA, viewModel.secondPhraseKana.get());
        outState.putString(KEY_THIRD_KANJI, viewModel.thirdPhraseKanji.get());
        outState.putString(KEY_THIRD_KANA, viewModel.thirdPhraseKana.get());
        outState.putString(KEY_FOURTH_KANJI, viewModel.fourthPhraseKanji.get());
        outState.putString(KEY_FOURTH_KANA, viewModel.fourthPhraseKana.get());
        outState.putString(KEY_FIFTH_KANJI, viewModel.fifthPhraseKanji.get());
        outState.putString(KEY_FIFTH_KANA, viewModel.fifthPhraseKana.get());
    }

    @Override
    public void onConfirmMaterialEditDialogPositiveClick() {
        viewModel.onClickDialogPositive();
    }

    @Override
    public void onConfirmMaterialEditDialogNegativeClick() {

    }

    private Karuta karuta() throws IllegalArgumentException {
        Bundle args = getArguments();
        if (args != null) {
            return args.getParcelable(ARG_KARUTA);
        }
        throw new IllegalArgumentException("argument is missing");
    }

    @ForFragment
    @dagger.Subcomponent(modules = {MaterialEditFragmentViewModelModule.class})
    public interface Subcomponent extends AndroidInjector<MaterialEditFragment> {

        @dagger.Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<MaterialEditFragment> {

            @SuppressWarnings("UnusedReturnValue")
            public abstract Subcomponent.Builder viewModelModule(MaterialEditFragmentViewModelModule module);

            @Override
            public void seedInstance(MaterialEditFragment instance) {
                viewModelModule(new MaterialEditFragmentViewModelModule(instance.karuta()));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @SuppressWarnings("unused")
        @Binds
        @IntoMap
        @FragmentKey(MaterialEditFragment.class)
        abstract AndroidInjector.Factory<? extends Fragment> bind(Subcomponent.Builder builder);
    }
}
