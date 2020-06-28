/*
 * Copyright (c) 2019. Rei Matsushita
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
package me.rei_m.hyakuninisshu.feature.materialedit.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.EventObserver
import me.rei_m.hyakuninisshu.feature.materialedit.databinding.MaterialEditFragmentBinding
import me.rei_m.hyakuninisshu.feature.materialedit.di.MaterialEditComponent
import javax.inject.Inject

class MaterialEditFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MaterialEditViewModel.Factory

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private var _binding: MaterialEditFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MaterialEditViewModel> {
        viewModelFactory.apply {
            orgMaterial = args.material
        }
    }

    private val args: MaterialEditFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as MaterialEditComponent.Injector)
            .materialEditComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.confirmEditEvent.observe(viewLifecycleOwner, EventObserver {
            val manager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view?.windowToken, 0)

            val action = MaterialEditFragmentDirections.actionMaterialEditToMaterialEditConfirm()
            findNavController().navigate(action)
        })
        viewModel.onCompleteEditEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().popBackStack()
        })
        viewModel.snackBarMessage.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.root, getString(it), Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show()
        })

        _binding = MaterialEditFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("MaterialEdit-${args.material.no}", requireActivity())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }
}
