package me.rei_m.hyakuninisshu.presentation.karuta.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import javax.inject.Inject;

import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.DialogConfirmMaterialEditBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.dialog.ConfirmMaterialEditDialogFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.dialog.di.ConfirmMaterialEditDialogFragmentViewModelModule;

public class ConfirmMaterialEditDialogFragment extends DialogFragment {

    public static final String TAG = "ConfirmMaterialEditDialogFragment";

    private static final String ARG_FIRST_KANJI = "firstKanji";
    private static final String ARG_FIRST_KANA = "firstKana";
    private static final String ARG_SECOND_KANJI = "secondKanji";
    private static final String ARG_SECOND_KANA = "secondKana";
    private static final String ARG_THIRD_KANJI = "thirdKanji";
    private static final String ARG_THIRD_KANA = "thirdKana";
    private static final String ARG_FOURTH_KANJI = "fourthKanji";
    private static final String ARG_FOURTH_KANA = "fourthKana";
    private static final String ARG_FIFTH_KANJI = "fifthKanji";
    private static final String ARG_FIFTH_KANA = "fifthKana";

    public static ConfirmMaterialEditDialogFragment newInstance(
            @NonNull String firstPhraseKanji,
            @NonNull String firstPhraseKana,
            @NonNull String secondPhraseKanji,
            @NonNull String secondPhraseKana,
            @NonNull String thirdPhraseKanji,
            @NonNull String thirdPhraseKana,
            @NonNull String fourthPhraseKanji,
            @NonNull String fourthPhraseKana,
            @NonNull String fifthPhraseKanji,
            @NonNull String fifthPhraseKana
    ) {
        ConfirmMaterialEditDialogFragment fragment = new ConfirmMaterialEditDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FIRST_KANJI, firstPhraseKanji);
        args.putString(ARG_FIRST_KANA, firstPhraseKana);
        args.putString(ARG_SECOND_KANJI, secondPhraseKanji);
        args.putString(ARG_SECOND_KANA, secondPhraseKana);
        args.putString(ARG_THIRD_KANJI, thirdPhraseKanji);
        args.putString(ARG_THIRD_KANA, thirdPhraseKana);
        args.putString(ARG_FOURTH_KANJI, fourthPhraseKanji);
        args.putString(ARG_FOURTH_KANA, fourthPhraseKana);
        args.putString(ARG_FIFTH_KANJI, fifthPhraseKanji);
        args.putString(ARG_FIFTH_KANA, fifthPhraseKana);
        fragment.setArguments(args);
        return fragment;
    }

    @dagger.Module
    public abstract class Module {
        @ForFragment
        @ContributesAndroidInjector(modules = ConfirmMaterialEditDialogFragmentViewModelModule.class)
        abstract ConfirmMaterialEditDialogFragment contributeInjector();
    }

    @Inject
    ConfirmMaterialEditDialogFragmentViewModel viewModel;

    private OnDialogInteractionListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        DialogConfirmMaterialEditBinding binding = DialogConfirmMaterialEditBinding.inflate(inflater, null, false);
        binding.setViewModel(viewModel);

        builder.setView(binding.getRoot())
                .setPositiveButton(R.string.update, (dialog, id) -> {
                    if (listener != null) {
                        listener.onConfirmMaterialEditDialogPositiveClick();
                    }
                })
                .setNegativeButton(R.string.back, (dialog, id) -> {
                    if (listener != null) {
                        listener.onConfirmMaterialEditDialogNegativeClick();
                    }
                });
        return builder.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        viewModel.onCreate(args.getString(ARG_FIRST_KANJI, ""),
                args.getString(ARG_FIRST_KANA, ""),
                args.getString(ARG_SECOND_KANJI, ""),
                args.getString(ARG_SECOND_KANA, ""),
                args.getString(ARG_THIRD_KANJI, ""),
                args.getString(ARG_THIRD_KANA, ""),
                args.getString(ARG_FOURTH_KANJI, ""),
                args.getString(ARG_FOURTH_KANA, ""),
                args.getString(ARG_FIFTH_KANJI, ""),
                args.getString(ARG_FIFTH_KANA, ""));
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
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (getTargetFragment() instanceof OnDialogInteractionListener) {
            listener = (OnDialogInteractionListener) getTargetFragment();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        viewModel = null;
        listener = null;
    }

    public interface OnDialogInteractionListener {
        void onConfirmMaterialEditDialogPositiveClick();

        void onConfirmMaterialEditDialogNegativeClick();
    }
}
