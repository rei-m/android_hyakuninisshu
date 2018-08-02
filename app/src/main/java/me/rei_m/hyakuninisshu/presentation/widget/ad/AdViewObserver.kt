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

package me.rei_m.hyakuninisshu.presentation.widget.ad

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import me.rei_m.hyakuninisshu.di.ForActivity
import javax.inject.Inject

@ForActivity
class AdViewObserver @Inject constructor(private val adViewFactory: AdViewFactory) : LifecycleObserver {

    private var adView: AdView? = null

    fun adView(): AdView {
        return adView ?: let {
            val adView = adViewFactory.create()
            this.adView = adView
            return@let adView
        }
    }

    fun loadAd() {
        adView?.let {
            val adRequest = AdRequest.Builder()
            it.loadAd(adRequest.build())
        }
    }

    fun showAd() {
        adView?.visibility = View.VISIBLE
    }

    fun hideAd() {
        adView?.visibility = View.GONE
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun releaseAd() {
        adView?.let {
            it.adListener = null
            val parent = it.parent as ViewGroup
            parent.removeView(adView)
            it.destroy()
        }
        adView = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resumeAd() {
        adView?.resume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pauseAd() {
        adView?.pause()
    }
}
