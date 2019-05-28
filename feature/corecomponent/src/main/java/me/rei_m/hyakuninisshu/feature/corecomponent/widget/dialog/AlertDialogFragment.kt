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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.feature.corecomponent.widget.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.withArgs

class AlertDialogFragment : DialogFragment() {

    private var listener: OnDialogInteractionListener? = null

    private val titleResId by lazy {
        requireNotNull(arguments?.getInt(ARG_TITLE)) {
            "$ARG_TITLE is missing"
        }
    }

    private val messageResId by lazy {
        requireNotNull(arguments?.getInt(ARG_MESSAGE)) {
            "$ARG_MESSAGE is missing"
        }
    }

    private val hasPositiveButton by lazy {
        requireNotNull(arguments?.getBoolean(ARG_HAS_POSITIVE_BUTTON)) {
            "$ARG_HAS_POSITIVE_BUTTON is missing"
        }
    }

    private val hasNegativeButton by lazy {
        requireNotNull(arguments?.getBoolean(ARG_HAS_NEGATIVE_BUTTON)) {
            "$ARG_HAS_NEGATIVE_BUTTON is missing"
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(titleResId)
            .setMessage(messageResId)
            .setCancelable(false)

        if (hasPositiveButton) {
            builder.setPositiveButton(android.R.string.ok) { _, _ ->
                listener?.onAlertPositiveClick()
            }
        }

        if (hasNegativeButton) {
            builder.setPositiveButton(android.R.string.cancel) { _, _ ->
                listener?.onAlertNegativeClick()
            }
        }

        return builder.create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnDialogInteractionListener) {
            listener = context
        } else if (targetFragment is OnDialogInteractionListener) {
            listener = targetFragment as OnDialogInteractionListener
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    interface OnDialogInteractionListener {
        fun onAlertPositiveClick()

        fun onAlertNegativeClick()
    }

    companion object {

        const val TAG: String = "AlertDialogFragment"

        private const val ARG_TITLE = "title"

        private const val ARG_MESSAGE = "message"

        private const val ARG_HAS_POSITIVE_BUTTON = "hasPositiveButton"

        private const val ARG_HAS_NEGATIVE_BUTTON = "hasNegativeButton"

        fun newInstance(
            @StringRes title: Int,
            @StringRes message: Int,
            hasPositiveButton: Boolean,
            hasNegativeButton: Boolean
        ) = AlertDialogFragment().withArgs {
            putInt(ARG_TITLE, title)
            putInt(ARG_MESSAGE, message)
            putBoolean(ARG_HAS_POSITIVE_BUTTON, hasPositiveButton)
            putBoolean(ARG_HAS_NEGATIVE_BUTTON, hasNegativeButton)
        }
    }
}
