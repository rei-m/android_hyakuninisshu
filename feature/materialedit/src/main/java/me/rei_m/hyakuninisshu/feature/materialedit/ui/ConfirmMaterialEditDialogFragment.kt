/*
 * Copyright (c) 2025. Rei Matsushita
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

package me.rei_m.hyakuninisshu.feature.materialedit.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import me.rei_m.hyakuninisshu.feature.materialedit.R
import me.rei_m.hyakuninisshu.feature.materialedit.databinding.DialogConfirmMaterialEditBinding

class ConfirmMaterialEditDialogFragment : DialogFragment() {
    private val viewModel by activityViewModels<MaterialEditViewModel>()

    private var _binding: DialogConfirmMaterialEditBinding? = null
    private val binding get() = _binding!!

    private var positiveClickListener: DialogInterface.OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val parentActivity = requireActivity()

        _binding =
            DialogConfirmMaterialEditBinding
                .inflate(parentActivity.layoutInflater, null, false)
        binding.viewModel = viewModel
        positiveClickListener =
            DialogInterface.OnClickListener { _, _ ->
                viewModel.onSubmitEdit()
            }

        val builder =
            MaterialAlertDialogBuilder(parentActivity)
                .setView(binding.root)
                .setPositiveButton(R.string.update) { v1, v2 ->
                    // LeakCanary先生に怒られるので怪しいところを潰している
                    positiveClickListener?.onClick(v1, v2)
                }.setNegativeButton(me.rei_m.hyakuninisshu.feature.corecomponent.R.string.back) { _, _ -> }

        return builder.create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        _binding = null
        positiveClickListener = null
        super.onDismiss(dialog)
    }
}
