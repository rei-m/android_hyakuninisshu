package me.rei_m.hyakuninisshu.presentation;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class AlertDialogFragment extends DialogFragment {

    public static final String TAG = AlertDialogFragment.class.getSimpleName();

    private static final String ARG_TITLE = "title";

    private static final String ARG_MESSAGE = "message";

    private static final String ARG_HAS_POSITIVE_BUTTON = "hasPositiveButton";

    private static final String ARG_HAS_NEGATIVE_BUTTON = "hasNegativeButton";

    public static AlertDialogFragment newInstance(@StringRes int title,
                                                  @StringRes int message,
                                                  boolean hasPositiveButton,
                                                  boolean hasNegativeButton) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, title);
        args.putInt(ARG_MESSAGE, message);
        args.putBoolean(ARG_HAS_POSITIVE_BUTTON, hasPositiveButton);
        args.putBoolean(ARG_HAS_NEGATIVE_BUTTON, hasNegativeButton);
        fragment.setArguments(args);
        return fragment;
    }

    private OnDialogInteractionListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int title = getArguments().getInt(ARG_TITLE);

        int message = getArguments().getInt(ARG_MESSAGE);

        boolean hasPositiveButton = getArguments().getBoolean(ARG_HAS_POSITIVE_BUTTON);

        boolean hasNegativeButton = getArguments().getBoolean(ARG_HAS_NEGATIVE_BUTTON);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false);

        if (hasPositiveButton) {
            builder.setPositiveButton(android.R.string.ok, (dialog, whichButton) -> {
                if (listener != null) {
                    listener.onDialogPositiveClick();
                }
            });
        }

        if (hasNegativeButton) {
            builder.setPositiveButton(android.R.string.cancel, (dialog, whichButton) -> {
                if (listener != null) {
                    listener.onDialogNegativeClick();
                }
            });
        }

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogInteractionListener) {
            listener = (OnDialogInteractionListener) context;
        } else if (getTargetFragment() instanceof OnDialogInteractionListener) {
            listener = (OnDialogInteractionListener) getTargetFragment();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnDialogInteractionListener {
        void onDialogPositiveClick();

        void onDialogNegativeClick();
    }
}
