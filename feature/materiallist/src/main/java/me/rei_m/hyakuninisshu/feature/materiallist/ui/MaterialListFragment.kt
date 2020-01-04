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
package me.rei_m.hyakuninisshu.feature.materiallist.ui

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.ColorFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.provideActivityViewModel
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.materiallist.databinding.FragmentMaterialListBinding
import me.rei_m.hyakuninisshu.feature.materiallist.helper.Navigator
import javax.inject.Inject

class MaterialListFragment : DaggerFragment(), MaterialListAdapter.OnItemInteractionListener {

    @Inject
    lateinit var viewModelFactory: MaterialListViewModel.Factory

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private lateinit var viewModel: MaterialListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) {
            viewModelFactory.colorFilter = ColorFilter[savedInstanceState.getInt(KEY_MATERIAL_COLOR_FILTER)]
        }
        viewModel = provideActivityViewModel(MaterialListViewModel::class.java, viewModelFactory)

        val binding = FragmentMaterialListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        with(binding.recyclerKarutaList) {
            adapter = MaterialListAdapter(requireContext(), listOf(), this@MaterialListFragment)
            addItemDecoration(DividerItemDecoration(inflater.context, DividerItemDecoration.VERTICAL))
        }

        setHasOptionsMenu(true)

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



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        for (colorFilter in ColorFilter.values()) {
            val menuItem = menu.add(Menu.NONE, colorFilter.ordinal, Menu.NONE, colorFilter.label(resources))
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
            menuItem.setOnMenuItemClickListener {
                viewModel.colorFilter = colorFilter
                false
            }
        }
    }

    override fun onItemClicked(position: Int) {
        navigator.navigateToMaterialDetail(position, viewModel.colorFilter)
    }

    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): MaterialListFragment
    }

    companion object {

        const val TAG: String = "MaterialListFragment"

        private const val KEY_MATERIAL_COLOR_FILTER = "materialColorFilter"

        fun newInstance() = MaterialListFragment()
    }
}
