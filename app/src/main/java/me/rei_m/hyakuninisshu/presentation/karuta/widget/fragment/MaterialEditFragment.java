package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialEditBinding;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.dialog.ConfirmMaterialEditDialogFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialEditFragmentViewModel;

public class MaterialEditFragment extends DaggerFragment implements ConfirmMaterialEditDialogFragment.OnDialogInteractionListener {

    public static final String TAG = "MaterialEditFragment";

    private static final String ARG_KARUTA_NO = "karutaNo";

    private static final int INVALID_KARUTA_NO = -1;

    public static MaterialEditFragment newInstance(int karutaNo) {
        MaterialEditFragment fragment = new MaterialEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_KARUTA_NO, karutaNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    MaterialEditFragmentViewModel viewModel;

    private FragmentMaterialEditBinding binding;

    private OnFragmentInteractionListener listener;

    private CompositeDisposable disposable;

    private int karutaNo = INVALID_KARUTA_NO;

    public MaterialEditFragment() {
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMaterialEditBinding.inflate(inflater, container, false);
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
        disposable.addAll(viewModel.onClickEditEvent.subscribe(v -> {
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
                    fragment.show(getFragmentManager(), ConfirmMaterialEditDialogFragment.TAG);
                }), viewModel.onErrorEditEvent.subscribe(v -> {
                    if (getView() != null) {
                        Snackbar.make(getView(), getString(R.string.text_message_edit_error), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null)
                                .show();
                    }
                }),
                viewModel.onUpdateMaterialEvent.subscribe(v -> getActivity().finish()));
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
        viewModel = null;
        listener = null;
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
}
