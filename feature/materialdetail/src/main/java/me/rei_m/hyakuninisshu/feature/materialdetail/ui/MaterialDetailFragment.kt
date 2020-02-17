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
package me.rei_m.hyakuninisshu.feature.materialdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.map
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.withArgs
import me.rei_m.hyakuninisshu.feature.materialdetail.databinding.FragmentMaterialDetailBinding
import javax.inject.Inject

class MaterialDetailFragment : DaggerFragment() {

    @Inject
    lateinit var store: MaterialDetailStore

    private var _binding: FragmentMaterialDetailBinding? = null
    private val binding get() = _binding!!

    private val karutaId by lazy {
        requireNotNull(arguments?.getParcelable<KarutaIdentifier>(ARG_KARUTA_ID)) {
            "$ARG_KARUTA_ID is missing"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMaterialDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        store.karutaList.map {
            it.find { karuta -> karuta.identifier == karutaId }
        }.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            binding.karuta = it
        })

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): MaterialDetailFragment
    }

    companion object {

        const val TAG: String = "MaterialDetailFragment"

        private const val ARG_KARUTA_ID = "karutaId"

        fun newInstance(karutaId: KarutaIdentifier) = MaterialDetailFragment().withArgs {
            putParcelable(ARG_KARUTA_ID, karutaId)
        }
    }
}
