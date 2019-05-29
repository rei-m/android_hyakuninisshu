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

package me.rei_m.hyakuninisshu.feature.materiallist.helper

import androidx.appcompat.app.AppCompatActivity
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.ColorFilter
import me.rei_m.hyakuninisshu.feature.materialdetail.ui.MaterialDetailActivity
import javax.inject.Inject

@ActivityScope
class Navigator @Inject constructor(private val activity: AppCompatActivity) {
    fun navigateToMaterialDetail(
        position: Int,
        colorFilter: ColorFilter
    ) {
        val intentToLaunch = MaterialDetailActivity.createIntent(activity, position, colorFilter)
        activity.startActivity(intentToLaunch)
    }
}
