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

package me.rei_m.hyakuninisshu.presentation.karuta

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.databinding.FragmentKarutaBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.ext.FragmentExt
import javax.inject.Inject

class KarutaFragment : DaggerFragment(), FragmentExt {

    @Inject
    lateinit var storeFactory: KarutaStore.Factory

    @Inject
    lateinit var viewModelFactory: KarutaViewModel.Factory

    private var listener: OnFragmentInteractionListener? = null

    private val karutaId: KarutaIdentifier
        get() = requireNotNull(arguments?.getParcelable(ARG_KARUTA_ID)) {
            "$ARG_KARUTA_ID is missing"
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewModel = viewModelFactory.create(obtainActivityStore(KarutaStore::class.java, storeFactory), karutaId)
        viewModel.notFoundKarutaEvent.observe(this, Observer {
            listener?.onError()
        })

        val binding = FragmentKarutaBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@KarutaFragment)
        }

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    @dagger.Module
    abstract class Module {
        @ForFragment
        @ContributesAndroidInjector
        abstract fun contributeInjector(): KarutaFragment
    }

    interface OnFragmentInteractionListener {
        fun onError()
    }

    companion object : FragmentExt {

        val TAG: String = KarutaFragment::class.java.simpleName

        private const val ARG_KARUTA_ID = "karutaId"

        fun newInstance(karutaId: KarutaIdentifier) = KarutaFragment().withArgs {
            putParcelable(ARG_KARUTA_ID, karutaId)
        }
    }
}
