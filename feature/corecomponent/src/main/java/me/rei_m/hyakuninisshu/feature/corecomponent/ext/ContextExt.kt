/*
 * Copyright (c) 2019. Rei Matsushita
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
@file:Suppress("DEPRECATION")

package me.rei_m.hyakuninisshu.feature.corecomponent.ext

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import com.google.android.gms.ads.AdSize

val Context.adHeight
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = windowManager.currentWindowMetrics
        AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
            this,
            metrics.bounds.width()
        ).getHeightInPixels(this)
    } else {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = outMetrics.density
        val adWidthPixels = outMetrics.widthPixels.toFloat()
        val adWidth = (adWidthPixels / density).toInt()
        AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
            .getHeightInPixels(this)
    }

fun Context.windowSize(): Point {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val windowMetrics = windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        val insetsIgnores =
            windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        Point(
            bounds.width() - insetsIgnores.right - insetsIgnores.left,
            bounds.height() - insetsIgnores.top - insetsIgnores.bottom
        )
    } else {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        point
    }
}
