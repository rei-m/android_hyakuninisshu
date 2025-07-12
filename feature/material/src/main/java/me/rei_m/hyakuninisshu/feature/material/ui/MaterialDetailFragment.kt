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
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.material.R
import me.rei_m.hyakuninisshu.feature.material.databinding.MaterialDetailFragmentBinding
import me.rei_m.hyakuninisshu.feature.material.di.MaterialComponent
import me.rei_m.hyakuninisshu.feature.material.ui.MaterialDetailFragmentDirections
import me.rei_m.hyakuninisshu.state.material.model.Material
import javax.inject.Inject

class MaterialDetailFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: MaterialViewModel.Factory

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private val args: MaterialDetailFragmentArgs by navArgs()

    private var _binding: MaterialDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MaterialViewModel> { viewModelFactory }

    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as MaterialComponent.Injector)
            .materialComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
        currentPosition = args.position
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = MaterialDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val pagerAdapter = ScreenSlidePagerAdapter(this, listOf())
        binding.pager.adapter = pagerAdapter

        return binding.root
    }

    override fun onDestroyView() {
        binding.pager.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setUpOptionMenu()
        analyticsHelper.sendScreenView("MaterialDetail", requireActivity())
        binding.pager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPosition = position
                }
            },
        )
        viewModel.materialList.observe(
            viewLifecycleOwner,
            Observer {
                it ?: return@Observer
                (binding.pager.adapter as ScreenSlidePagerAdapter).replaceData(it)
                if (savedInstanceState == null) {
                    binding.pager.setCurrentItem(args.position, false)
                } else {
                    binding.pager.setCurrentItem(
                        savedInstanceState.getInt(ARG_POSITION, args.position),
                        false,
                    )
                }
            },
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ARG_POSITION, currentPosition)
        super.onSaveInstanceState(outState)
    }

    private fun setUpOptionMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(
                    menu: Menu,
                    menuInflater: MenuInflater,
                ) {
                    menuInflater.inflate(R.menu.material_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.material_detail_edit -> {
                            val material =
                                viewModel.materialList.value?.get(currentPosition) ?: return true
                            val action =
                                MaterialDetailFragmentDirections.actionMaterialDetailToMaterialEdit(
                                    material,
                                )
                            findNavController().navigate(action)
                        }
                    }
                    return true
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED,
        )
    }

    private inner class ScreenSlidePagerAdapter(
        fragment: Fragment,
        private var materialList: List<Material>,
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = materialList.size

        override fun createFragment(position: Int): Fragment = MaterialDetailPageFragment.newInstance(materialList[position])

        fun replaceData(materialList: List<Material>) {
            this.materialList = materialList
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val ARG_POSITION = "ARG_POSITION"
    }
}
