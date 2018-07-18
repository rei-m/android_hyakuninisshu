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

package me.rei_m.hyakuninisshu.presentation.widget.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class AlertDialogFragment : DialogFragment() {

    private var listener: OnDialogInteractionListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val title = arguments!!.getInt(ARG_TITLE)

        val message = arguments!!.getInt(ARG_MESSAGE)

        val hasPositiveButton = arguments!!.getBoolean(ARG_HAS_POSITIVE_BUTTON)

        val hasNegativeButton = arguments!!.getBoolean(ARG_HAS_NEGATIVE_BUTTON)

        val builder = AlertDialog.Builder(activity!!)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)

        if (hasPositiveButton) {
            builder.setPositiveButton(android.R.string.ok) { _, _ ->
                listener?.onDialogPositiveClick()
            }
        }

        if (hasNegativeButton) {
            builder.setPositiveButton(android.R.string.cancel) { _, _ ->
                listener?.onDialogNegativeClick()
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
        super.onDetach()
        listener = null
    }

    interface OnDialogInteractionListener {
        fun onDialogPositiveClick()

        fun onDialogNegativeClick()
    }

    companion object {

        val TAG: String = AlertDialogFragment::class.java.simpleName

        private const val ARG_TITLE = "title"

        private const val ARG_MESSAGE = "message"

        private const val ARG_HAS_POSITIVE_BUTTON = "hasPositiveButton"

        private const val ARG_HAS_NEGATIVE_BUTTON = "hasNegativeButton"

        fun newInstance(@StringRes title: Int,
                        @StringRes message: Int,
                        hasPositiveButton: Boolean,
                        hasNegativeButton: Boolean) = AlertDialogFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_TITLE, title)
                putInt(ARG_MESSAGE, message)
                putBoolean(ARG_HAS_POSITIVE_BUTTON, hasPositiveButton)
                putBoolean(ARG_HAS_NEGATIVE_BUTTON, hasNegativeButton)
            }
        }
    }
}
