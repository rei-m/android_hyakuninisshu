package me.rei_m.hyakuninisshu.presentation.support.widget.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.webkit.WebView;

import me.rei_m.hyakuninisshu.R;

public class LicenceDialogFragment extends DialogFragment {

    public static final String TAG = "LicenceDialogFragment";

    public static LicenceDialogFragment newInstance() {
        return new LicenceDialogFragment();
    }

    public LicenceDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        WebView webView = new WebView(getActivity());
        webView.loadUrl(getString(R.string.url_credit));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton(getString(R.string.back), null)
                .setView(webView);

        return builder.create();
    }
}
