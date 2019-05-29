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

package me.rei_m.hyakuninisshu.feature.trainingmenu.helper

import androidx.appcompat.app.AppCompatActivity
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.*
import me.rei_m.hyakuninisshu.feature.training.ui.TrainingActivity
import javax.inject.Inject

@ActivityScope
class Navigator @Inject constructor(private val activity: AppCompatActivity) {
    fun navigateToTraining(
        trainingRangeFrom: TrainingRangeFrom,
        trainingRangeTo: TrainingRangeTo,
        kimarijiFilter: KimarijiFilter,
        colorFilter: ColorFilter,
        kamiNoKuStyle: KarutaStyleFilter,
        shimoNoKuStyle: KarutaStyleFilter,
        animationSpeed: QuizAnimationSpeed
    ) {
        val intentToLaunch = TrainingActivity.createIntent(
            activity,
            trainingRangeFrom,
            trainingRangeTo,
            kimarijiFilter,
            colorFilter,
            kamiNoKuStyle,
            shimoNoKuStyle,
            animationSpeed
        )
        activity.startActivity(intentToLaunch)
    }
}
