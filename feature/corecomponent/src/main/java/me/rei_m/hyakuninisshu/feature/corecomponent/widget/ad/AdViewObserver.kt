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
package me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad

import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import javax.inject.Inject

@ActivityScope
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

//    private val adSize: AdSize
//        get() {
//            val display = windowManager.defaultDisplay
//            val outMetrics = DisplayMetrics()
//            display.getMetrics(outMetrics)
//
//            val density = outMetrics.density
//
//            var adWidthPixels = ad_view_container.width.toFloat()
//            if (adWidthPixels == 0f) {
//                adWidthPixels = outMetrics.widthPixels.toFloat()
//            }
//
//            val adWidth = (adWidthPixels / density).toInt()
//            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
//        }
}
