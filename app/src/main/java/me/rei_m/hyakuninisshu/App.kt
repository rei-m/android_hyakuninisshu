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
package me.rei_m.hyakuninisshu

import android.content.Context
import androidx.multidex.MultiDex
import com.google.android.gms.ads.MobileAds
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import me.rei_m.hyakuninisshu.action.di.ActionModule
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ViewModelModule
import me.rei_m.hyakuninisshu.feature.entrance.ui.EntranceActivity
import me.rei_m.hyakuninisshu.feature.exam.ui.ExamActivity
import me.rei_m.hyakuninisshu.feature.examhistory.ui.ExamHistoryActivity
import me.rei_m.hyakuninisshu.feature.karuta.ui.KarutaActivity
import me.rei_m.hyakuninisshu.feature.materialdetail.ui.MaterialDetailActivity
import me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditActivity
import me.rei_m.hyakuninisshu.feature.splash.ui.SplashActivity
import me.rei_m.hyakuninisshu.feature.training.ui.TrainingActivity
import me.rei_m.hyakuninisshu.feature.training.ui.TrainingExamActivity
import me.rei_m.hyakuninisshu.infrastructure.di.InfrastructureModule
import me.rei_m.hyakuninisshu.util.CrashlyticsTree
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Singleton

open class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initAdMob()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<App> =
        DaggerApp_AppComponent.factory().create(applicationContext)

    protected open fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }

    protected open fun initAdMob() {
        MobileAds.initialize(this, getString(R.string.ad_mob_app_id))
    }

    @Singleton
    @Component(
        modules = [
            AndroidSupportInjectionModule::class,
            InfrastructureModule::class,
            ActionModule::class,
            ViewModelModule::class,
            SplashActivity.Module::class,
            EntranceActivity.Module::class,
            ExamActivity.Module::class,
            ExamHistoryActivity.Module::class,
            MaterialDetailActivity.Module::class,
            MaterialEditActivity.Module::class,
            KarutaActivity.Module::class,
            TrainingExamActivity.Module::class,
            TrainingActivity.Module::class
        ]
    )
    interface AppComponent : AndroidInjector<App> {
        @Component.Factory
        interface Factory {
            fun create(@BindsInstance context: Context): AppComponent
        }
    }
}
