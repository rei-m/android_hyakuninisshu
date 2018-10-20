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

import com.squareup.leakcanary.LeakCanary

import javax.inject.Singleton

import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import me.rei_m.hyakuninisshu.di.ApplicationModule
import me.rei_m.hyakuninisshu.infrastructure.di.InfrastructureModule
import me.rei_m.hyakuninisshu.presentation.entrance.EntranceActivity
import me.rei_m.hyakuninisshu.presentation.SplashActivity
import me.rei_m.hyakuninisshu.presentation.exam.ExamActivity
import me.rei_m.hyakuninisshu.presentation.examhistory.ExamHistoryActivity
import me.rei_m.hyakuninisshu.presentation.karuta.KarutaActivity
import me.rei_m.hyakuninisshu.presentation.materialdetail.MaterialDetailActivity
import me.rei_m.hyakuninisshu.presentation.materialedit.MaterialEditActivity
import me.rei_m.hyakuninisshu.presentation.training.TrainingExamActivity
import me.rei_m.hyakuninisshu.presentation.training.TrainingActivity
import me.rei_m.hyakuninisshu.util.CrashlyticsTree
import timber.log.Timber
import timber.log.Timber.DebugTree

open class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        initLeakCanary()
        initTimber()
        initAdMob()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerApp_Component.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    protected open fun initLeakCanary() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }
    }

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
    @dagger.Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ApplicationModule::class,
            InfrastructureModule::class,
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
    interface Component : AndroidInjector<App>
}
