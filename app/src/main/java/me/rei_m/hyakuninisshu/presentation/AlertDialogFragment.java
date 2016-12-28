package me.rei_m.hyakuninisshu.presentation;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class AlertDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "title";

    private static final String ARG_MESSAGE = "message";

    public static AlertDialogFragment newInstance(@StringRes int title,
                                                  @StringRes int message) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, title);
        args.putInt(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int title = getArguments().getInt(ARG_TITLE);

        int message = getArguments().getInt(ARG_MESSAGE);

        Activity activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false);

        if (activity instanceof OnClickPositiveButtonListener) {
            builder.setPositiveButton(android.R.string.ok, (dialog, whichButton) -> {
                ((OnClickPositiveButtonListener) activity).onClickPositiveButton();
            });
        }

        if (activity instanceof OnClickNegativeButtonListener) {
            builder.setPositiveButton(android.R.string.cancel, (dialog, whichButton) -> {
                ((OnClickNegativeButtonListener) activity).onClickNegativeButton();
            });
        }

        return builder.create();
    }

    public interface OnClickPositiveButtonListener {
        void onClickPositiveButton();
    }

    public interface OnClickNegativeButtonListener {
        void onClickNegativeButton();
    }
}
