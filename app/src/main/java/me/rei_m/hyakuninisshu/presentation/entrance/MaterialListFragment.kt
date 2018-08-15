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

package me.rei_m.hyakuninisshu.presentation.entrance

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.*
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialListBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import me.rei_m.hyakuninisshu.util.AnalyticsHelper
import javax.inject.Inject

class MaterialListFragment : DaggerFragment() {

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var viewModelFactory: MaterialListViewModel.Factory

    private lateinit var viewModel: MaterialListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val colorFilter = if (savedInstanceState == null) {
            ColorFilter.ALL
        } else {
            ColorFilter[savedInstanceState.getInt(KEY_MATERIAL_COLOR_FILTER)]
        }

        viewModel = viewModelFactory.create(requireActivity(), colorFilter)

        val binding = FragmentMaterialListBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@MaterialListFragment)
        }
        setHasOptionsMenu(true)

        with(binding.recyclerKarutaList) {
            adapter = MaterialListAdapter(requireContext(), listOf(), viewModel)
            addItemDecoration(DividerItemDecoration(inflater.context, DividerItemDecoration.VERTICAL))
        }

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("Entrance - MaterialList", requireActivity())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_MATERIAL_COLOR_FILTER, viewModel.colorFilter.ordinal)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        for (colorFilter in ColorFilter.values()) {
            val menuItem = menu!!.add(Menu.NONE, colorFilter.ordinal, Menu.NONE, colorFilter.label(resources))
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
            menuItem.setOnMenuItemClickListener {
                viewModel.colorFilter = colorFilter
                false
            }
        }
    }

    @dagger.Module
    abstract class Module {
        @ForFragment
        @ContributesAndroidInjector
        abstract fun contributeInjector(): MaterialListFragment
    }

    companion object {

        const val TAG: String = "MaterialListFragment"

        private const val KEY_MATERIAL_COLOR_FILTER = "materialColorFilter"

        fun newInstance(): MaterialListFragment = MaterialListFragment()
    }
}
