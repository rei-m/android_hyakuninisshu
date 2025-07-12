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

package me.rei_m.hyakuninisshu

import android.app.Application
import com.google.android.gms.ads.MobileAds
import me.rei_m.hyakuninisshu.di.AppComponent
import me.rei_m.hyakuninisshu.di.DaggerAppComponent
import timber.log.Timber

open class App : Application() {
    val appComponent: AppComponent by lazy { initializeComponent() }

    open fun initializeComponent(): AppComponent = DaggerAppComponent.factory().create(applicationContext)

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initAdMob()
    }

    protected open fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        // FB Crashlytics と連携させる・・・そのうち
    }

    protected open fun initAdMob() {
        MobileAds.initialize(this) {
            Timber.i("initialize Ad")
        }
    }
}
