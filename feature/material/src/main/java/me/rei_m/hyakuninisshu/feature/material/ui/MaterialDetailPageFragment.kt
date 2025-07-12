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

package me.rei_m.hyakuninisshu.feature.material.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import me.rei_m.hyakuninisshu.feature.material.databinding.MaterialDetailPageFragmentBinding
import me.rei_m.hyakuninisshu.state.material.model.Material

class MaterialDetailPageFragment : Fragment() {
    private val args: MaterialDetailPageFragmentArgs by navArgs()

    private var _binding: MaterialDetailPageFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = MaterialDetailPageFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        // ここなくてもいいかも
        val materialFromBundle = requireArguments().getParcelable<Material>(ARG_MATERIAL)
        if (materialFromBundle == null) {
            binding.material = args.material
        } else {
            binding.material = materialFromBundle
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val ARG_MATERIAL = "material"

        fun newInstance(material: Material) =
            MaterialDetailPageFragment().apply {
                arguments =
                    Bundle().apply {
                        putParcelable(ARG_MATERIAL, material)
                    }
            }
    }
}
