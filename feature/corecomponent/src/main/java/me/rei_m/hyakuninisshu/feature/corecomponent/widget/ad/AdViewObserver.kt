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

import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import me.rei_m.hyakuninisshu.feature.corecomponent.R

class AdViewObserver : DefaultLifecycleObserver {

    private var adView: AdView? = null

    private var isLoadedAd = false

    fun showAd(activity: AppCompatActivity, container: FrameLayout) {
        setup(activity, container)

        adView?.let {
            if (!it.isLoading && !isLoadedAd) {
                loadAd()
            }
            it.visibility = View.VISIBLE
        }
    }

    fun hideAd() {
        adView?.visibility = View.GONE
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        adView?.destroy()
        isLoadedAd = false
        adView = null
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        adView?.resume()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        adView?.pause()
    }

    private fun setup(activity: AppCompatActivity, container: FrameLayout) {
        if (adView == null) {
            val adView = AdView(activity).apply {
                adUnitId = activity.getString(R.string.banner_ad_unit_id)
                setAdSize(calcAdSize(activity, container))
                adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        isLoadedAd = true
                    }
                }
            }
            container.addView(adView)

            this.adView = adView
        }
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)
    }

    private fun calcAdSize(activity: AppCompatActivity, container: FrameLayout): AdSize {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowManager = activity.windowManager
            val metrics = windowManager.currentWindowMetrics
            val bounds = metrics.bounds
            var adWidthPixels = container.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = bounds.width().toFloat()
            }
            val density = container.resources.displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()
            AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                activity,
                adWidth
            )
        } else {
            val windowManager = activity.windowManager
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
            val density = outMetrics.density
            val adWidthPixels = outMetrics.widthPixels.toFloat()
            val adWidth = (adWidthPixels / density).toInt()
            AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
        }
    }
}
