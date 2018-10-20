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
package me.rei_m.hyakuninisshu.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

interface ViewModelFactory {
    fun <T : ViewModel> obtainActivityStore(
        activity: FragmentActivity,
        viewModelClass: Class<T>,
        factory: ViewModelProvider.Factory
    ) = ViewModelProviders.of(activity, factory).get(viewModelClass)

    fun <T : ViewModel> obtainFragmentStore(
        fragment: Fragment,
        viewModelClass: Class<T>,
        factory: ViewModelProvider.Factory
    ) = ViewModelProviders.of(fragment, factory).get(viewModelClass)
}
