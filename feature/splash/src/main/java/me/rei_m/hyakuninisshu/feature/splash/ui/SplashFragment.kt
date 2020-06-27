/*
 * Copyright (c) 2020. Rei Matsushita.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package me.rei_m.hyakuninisshu.feature.splash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.EventObserver
import me.rei_m.hyakuninisshu.feature.splash.R
import me.rei_m.hyakuninisshu.feature.splash.databinding.SplashFragmentBinding
import me.rei_m.hyakuninisshu.feature.splash.di.SplashComponent
import javax.inject.Inject

class SplashFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: SplashViewModel.Factory

    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<SplashViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as SplashComponent.Injector)
            .splashComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        viewModel.onReadyEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(R.id.action_splash_to_trainingMenu)
        })
    }
}
