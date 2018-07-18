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

package me.rei_m.hyakuninisshu.presentation.materialdetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialDetailBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.ext.FragmentExt

class MaterialDetailFragment : DaggerFragment() {

    private val karutaIdentifier: KarutaIdentifier
        get() = arguments?.getParcelable(ARG_KARUTA_ID) ?: let {
            throw IllegalArgumentException("argument karuta id is missing")
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMaterialDetailBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@MaterialDetailFragment)
        }

        Transformations.map((activity as MaterialDetailActivity).viewModel.karutaList) {
            it.find { it.identifier() == karutaIdentifier }
        }.observe(this, Observer { binding.karuta = it })

        return binding.root
    }

    @dagger.Module
    abstract class Module {
        @ForFragment
        @ContributesAndroidInjector
        abstract fun contributeInjector(): MaterialDetailFragment
    }

    companion object : FragmentExt {

        val TAG: String = MaterialDetailFragment::class.java.simpleName

        private const val ARG_KARUTA_ID = "karutaId"

        fun newInstance(karutaId: KarutaIdentifier) = MaterialDetailFragment().withArgs {
            putParcelable(ARG_KARUTA_ID, karutaId)
        }
    }
}
