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

package me.rei_m.hyakuninisshu.presentation

import android.app.Activity
import android.arch.lifecycle.Observer
import android.os.Bundle
import dagger.Binds
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.IntoMap
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.action.application.ApplicationActionDispatcher
import me.rei_m.hyakuninisshu.di.ForActivity
import me.rei_m.hyakuninisshu.ext.AppCompatActivityExt
import me.rei_m.hyakuninisshu.presentation.di.ActivityModule
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity(), AppCompatActivityExt {

    @Inject
    lateinit var storeFactory: ApplicationStore.Factory

    @Inject
    lateinit var actionDispatcher: ApplicationActionDispatcher

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val store = obtainStore(ApplicationStore::class.java, storeFactory)
        store.isReady.observe(this, Observer {
            if (it == true) {
                navigator.navigateToEntrance()
                finish()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        actionDispatcher.start()
    }

    @ForActivity
    @dagger.Subcomponent(modules = [ActivityModule::class])
    interface Subcomponent : AndroidInjector<SplashActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Builder<SplashActivity>() {

            abstract fun activityModule(module: ActivityModule): Builder

            override fun seedInstance(instance: SplashActivity) {
                activityModule(ActivityModule(instance))
            }
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract inner class Module {
        @Binds
        @IntoMap
        @ActivityKey(SplashActivity::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<out Activity>
    }
}