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

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.webkit.WebView;

import me.rei_m.hyakuninisshu.R;

public class LicenceDialogFragment extends DialogFragment {

    public static final String TAG = LicenceDialogFragment.class.getSimpleName();

    public static LicenceDialogFragment newInstance() {
        return new LicenceDialogFragment();
    }

    public LicenceDialogFragment() {
        // Required empty public constructor
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
