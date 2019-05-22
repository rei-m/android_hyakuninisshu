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

import android.content.Context

import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import me.rei_m.hyakuninisshu.feature.corecomponent.R

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AdViewFactory @Inject constructor(private val context: Context) {
    fun create(): AdView = AdView(context).apply {
        adSize = AdSize.SMART_BANNER
        adUnitId = context.getString(R.string.banner_ad_unit_id)
    }
}
