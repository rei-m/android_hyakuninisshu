/*
 * Copyright (c) 2018. Rei Matsushita
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

package me.rei_m.hyakuninisshu.presentation.entrance

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.webkit.WebView

import me.rei_m.hyakuninisshu.R

class LicenceDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val activityContext = requireActivity()

        val webView = WebView(activityContext).apply {
            loadUrl(getString(R.string.url_credit))
        }

        val builder = AlertDialog.Builder(activityContext)
                .setPositiveButton(getString(R.string.back), null)
                .setView(webView)

        return builder.create()
    }

    companion object {

        val TAG: String = LicenceDialogFragment::class.java.simpleName

        fun newInstance(): LicenceDialogFragment = LicenceDialogFragment()
    }
}
