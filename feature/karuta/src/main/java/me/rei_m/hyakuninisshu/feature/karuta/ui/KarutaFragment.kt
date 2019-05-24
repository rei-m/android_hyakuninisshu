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
package me.rei_m.hyakuninisshu.feature.karuta.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.provideActivityViewModel
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.withArgs
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.EventObserver
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.karuta.databinding.FragmentKarutaBinding
import javax.inject.Inject

class KarutaFragment : DaggerFragment() {

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var viewModelFactory: KarutaViewModel.Factory

    private var listener: OnFragmentInteractionListener? = null

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
        val karutaViewModel = provideActivityViewModel(KarutaViewModel::class.java, viewModelFactory)

        val binding = FragmentKarutaBinding.inflate(inflater, container, false).apply {
            viewModel = karutaViewModel
            setLifecycleOwner(this@KarutaFragment.viewLifecycleOwner)
        }

        karutaViewModel.notFoundKarutaEvent.observe(this,
            EventObserver {
                listener?.onError()
            })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("Karuta-${karutaId.value}", requireActivity())
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
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): KarutaFragment
    }

    interface OnFragmentInteractionListener {
        fun onError()
    }

    companion object {

        const val TAG: String = "KarutaFragment"

        private const val ARG_KARUTA_ID = "karutaId"

        fun newInstance(karutaId: KarutaIdentifier) = KarutaFragment().withArgs {
            putParcelable(ARG_KARUTA_ID, karutaId)
        }
    }
}
