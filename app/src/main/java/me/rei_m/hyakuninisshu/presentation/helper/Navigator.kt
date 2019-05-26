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
package me.rei_m.hyakuninisshu.presentation.helper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.*
import me.rei_m.hyakuninisshu.feature.karuta.ui.KarutaActivity
import me.rei_m.hyakuninisshu.feature.training.ui.TrainingActivity
import me.rei_m.hyakuninisshu.feature.training.ui.TrainingExamActivity
import me.rei_m.hyakuninisshu.presentation.entrance.EntranceActivity
import me.rei_m.hyakuninisshu.presentation.exam.ExamActivity
import me.rei_m.hyakuninisshu.presentation.examhistory.ExamHistoryActivity

class Navigator(private val activity: AppCompatActivity) {

    fun navigateToEntrance() {
        val intentToLaunch = EntranceActivity.createIntent(activity)
        intentToLaunch.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intentToLaunch)
    }

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

    fun navigateToExam() {
        val intentToLaunch = ExamActivity.createIntent(activity)
        activity.startActivity(intentToLaunch)
    }

    fun navigateToExamHistory() {
        val intentToLaunch = ExamHistoryActivity.createIntent(activity)
        activity.startActivity(intentToLaunch)
    }

    fun navigateToTrainingExam() {
        val intentToLaunch = TrainingExamActivity.createIntent(activity)
        activity.startActivity(intentToLaunch)
    }

    fun navigateToKaruta(karutaId: KarutaIdentifier) {
        val intentToLaunch = KarutaActivity.createIntent(activity, karutaId)
        activity.startActivity(intentToLaunch)
    }

    fun back() {
        activity.finish()
    }
}
