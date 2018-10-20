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
package me.rei_m.hyakuninisshu.presentation.materialedit

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.databinding.DialogConfirmMaterialEditBinding

class ConfirmMaterialEditDialogFragment : DialogFragment() {

    private var listener: OnDialogInteractionListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val parentActivity = requireActivity()

        val binding = DialogConfirmMaterialEditBinding
            .inflate(parentActivity.layoutInflater, null, false)

        with(binding) {
            firstPhraseKanji = arguments?.getString(ARG_FIRST_KANJI)
            firstPhraseKana = arguments?.getString(ARG_FIRST_KANA)
            secondPhraseKanji = arguments?.getString(ARG_SECOND_KANJI)
            secondPhraseKana = arguments?.getString(ARG_SECOND_KANA)
            thirdPhraseKanji = arguments?.getString(ARG_THIRD_KANJI)
            thirdPhraseKana = arguments?.getString(ARG_THIRD_KANA)
            fourthPhraseKanji = arguments?.getString(ARG_FOURTH_KANJI)
            fourthPhraseKana = arguments?.getString(ARG_FOURTH_KANA)
            fifthPhraseKanji = arguments?.getString(ARG_FIFTH_KANJI)
            fifthPhraseKana = arguments?.getString(ARG_FIFTH_KANA)
        }

        val builder = AlertDialog.Builder(parentActivity)
            .setView(binding.root)
            .setPositiveButton(R.string.update) { _, _ ->
                listener?.onClickUpdate()
            }
            .setNegativeButton(R.string.back) { _, _ ->
                listener?.onClickBack()
            }

        return builder.create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val parentFragment = targetFragment
        if (parentFragment is OnDialogInteractionListener) {
            listener = parentFragment
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    interface OnDialogInteractionListener {
        fun onClickUpdate()

        fun onClickBack()
    }

    companion object {

        const val TAG: String = "ConfirmMaterialEditDialogFragment"

        private const val ARG_FIRST_KANJI = "firstKanji"
        private const val ARG_FIRST_KANA = "firstKana"
        private const val ARG_SECOND_KANJI = "secondKanji"
        private const val ARG_SECOND_KANA = "secondKana"
        private const val ARG_THIRD_KANJI = "thirdKanji"
        private const val ARG_THIRD_KANA = "thirdKana"
        private const val ARG_FOURTH_KANJI = "fourthKanji"
        private const val ARG_FOURTH_KANA = "fourthKana"
        private const val ARG_FIFTH_KANJI = "fifthKanji"
        private const val ARG_FIFTH_KANA = "fifthKana"

        fun newInstance(
            firstKanji: String,
            firstKana: String,
            secondKanji: String,
            secondKana: String,
            thirdKanji: String,
            thirdKana: String,
            fourthKanji: String,
            fourthKana: String,
            fifthKanji: String,
            fifthKana: String
        ) = ConfirmMaterialEditDialogFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_FIRST_KANJI, firstKanji)
                putString(ARG_FIRST_KANA, firstKana)
                putString(ARG_SECOND_KANJI, secondKanji)
                putString(ARG_SECOND_KANA, secondKana)
                putString(ARG_THIRD_KANJI, thirdKanji)
                putString(ARG_THIRD_KANA, thirdKana)
                putString(ARG_FOURTH_KANJI, fourthKanji)
                putString(ARG_FOURTH_KANA, fourthKana)
                putString(ARG_FIFTH_KANJI, fifthKanji)
                putString(ARG_FIFTH_KANA, fifthKana)
            }
        }
    }
}
