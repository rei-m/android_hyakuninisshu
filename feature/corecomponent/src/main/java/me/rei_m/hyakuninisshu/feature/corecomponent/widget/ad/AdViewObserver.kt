/*
 * Copyright (c) 2020. Rei Matsushita
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
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import me.rei_m.hyakuninisshu.feature.corecomponent.R
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class AdViewObserver @Inject constructor() :
    LifecycleObserver {

    private var adView: AdView? = null

    fun loadAd(activity: AppCompatActivity, container: FrameLayout) {
        if (adView == null) {
            val adView = AdView(activity)
            container.addView(adView)
            adView.adUnitId = activity.getString(R.string.banner_ad_unit_id)
            adView.adSize = calcAdSize(activity, container)
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
            this.adView = adView
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
        adView?.adListener = null
        adView?.destroy()
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

    private fun calcAdSize(activity: AppCompatActivity, container: FrameLayout): AdSize {
        val display = activity.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val density = outMetrics.density

        var adWidthPixels = container.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }

        val adWidth = (adWidthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
    }
}
