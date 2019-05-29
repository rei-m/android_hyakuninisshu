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
package me.rei_m.hyakuninisshu.feature.splash.ui

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Observer
import dagger.Binds
import dagger.multibindings.ClassKey
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.IntoMap
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityModule
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.provideViewModel
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.showAlertDialog
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.EventObserver
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.dialog.AlertDialogFragment
import me.rei_m.hyakuninisshu.feature.splash.R
import me.rei_m.hyakuninisshu.feature.splash.helper.Navigator
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity(),
    AlertDialogFragment.OnDialogInteractionListener {

    @Inject
    lateinit var viewModelFactory: ApplicationViewModel.Factory

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val viewModel = provideViewModel(ApplicationViewModel::class.java, viewModelFactory)
        viewModel.isReady.observe(this, Observer {
            if (it == true) {
                navigator.navigateToEntrance()
                finish()
            }
        })
        viewModel.unhandledErrorEvent.observe(this, EventObserver {
            showAlertDialog(R.string.text_title_error, R.string.text_message_boot_error)
        })
    }

    override fun onAlertPositiveClick() {
        finish()
    }

    override fun onAlertNegativeClick() {
        // Negative Button is disable.
    }

    @ActivityScope
    @dagger.Subcomponent(
        modules = [
            ActivityModule::class
        ]
    )
    interface Subcomponent : AndroidInjector<SplashActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Factory<SplashActivity> {
            override fun create(instance: SplashActivity): AndroidInjector<SplashActivity> =
                activityModule(ActivityModule(instance)).build()

            abstract fun activityModule(module: ActivityModule): Builder

            abstract fun build(): AndroidInjector<SplashActivity>
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract inner class Module {
        @Binds
        @IntoMap
        @ClassKey(SplashActivity::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<*>
    }
}
