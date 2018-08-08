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

package me.rei_m.hyakuninisshu.presentation.examhistory

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.databinding.FragmentExamHistoryBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import javax.inject.Inject

class ExamHistoryFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ExamHistoryViewModel.Factory

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val viewModel = viewModelFactory.create(requireActivity())
        viewModel.unhandledErrorEvent.observe(this, Observer {
            listener?.onError()
        })

        val binding = FragmentExamHistoryBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@ExamHistoryFragment)
        }

        with(binding.recyclerKarutaExamList) {
            adapter = KarutaExamListAdapter(listOf())
            addItemDecoration(DividerItemDecoration(inflater.context, DividerItemDecoration.VERTICAL))
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
        abstract fun contributeInjector(): ExamHistoryFragment
    }

    interface OnFragmentInteractionListener {
        fun onError()
    }

    companion object {

        const val TAG: String = "ExamHistoryFragment"

        fun newInstance() = ExamHistoryFragment()
    }
}
