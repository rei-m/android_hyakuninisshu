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

package me.rei_m.hyakuninisshu.presentation.helper

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity

import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.presentation.entrance.EntranceActivity
import me.rei_m.hyakuninisshu.presentation.entrance.LicenceDialogFragment
import me.rei_m.hyakuninisshu.presentation.exam.ExamActivity
import me.rei_m.hyakuninisshu.presentation.karuta.KarutaActivity
import me.rei_m.hyakuninisshu.presentation.materialdetail.MaterialDetailActivity
import me.rei_m.hyakuninisshu.presentation.materialedit.MaterialEditActivity
import me.rei_m.hyakuninisshu.presentation.training.TrainingExamActivity
import me.rei_m.hyakuninisshu.presentation.training.TrainingActivity
import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import me.rei_m.hyakuninisshu.presentation.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.presentation.enums.KimarijiFilter
import me.rei_m.hyakuninisshu.presentation.enums.TrainingRangeFrom
import me.rei_m.hyakuninisshu.presentation.enums.TrainingRangeTo

class Navigator(private val activity: AppCompatActivity) {

    fun navigateToEntrance() {
        val intentToLaunch = EntranceActivity.createIntent(activity)
        intentToLaunch.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intentToLaunch)
    }

    fun navigateToTrainingMaster(trainingRangeFrom: TrainingRangeFrom,
                                 trainingRangeTo: TrainingRangeTo,
                                 kimarijiFilter: KimarijiFilter,
                                 colorFilter: ColorFilter,
                                 kamiNoKuStyle: KarutaStyleFilter,
                                 shimoNoKuStyle: KarutaStyleFilter) {
        val intentToLaunch = TrainingActivity.createIntent(activity,
                trainingRangeFrom,
                trainingRangeTo,
                kimarijiFilter,
                colorFilter,
                kamiNoKuStyle,
                shimoNoKuStyle)
        activity.startActivity(intentToLaunch)
    }

    fun navigateToExamMaster() {
        val intentToLaunch = ExamActivity.createIntent(activity)
        activity.startActivity(intentToLaunch)
    }

    fun navigateToExamTrainingMaster() {
        val intentToLaunch = TrainingExamActivity.createIntent(activity)
        activity.startActivity(intentToLaunch)
    }

    fun navigateToMaterialDetail(position: Int,
                                 colorFilter: ColorFilter) {
        val intentToLaunch = MaterialDetailActivity.createIntent(activity, position, colorFilter)
        activity.startActivity(intentToLaunch)
    }

    fun navigateToMaterialEdit(karutaId: KarutaIdentifier) {
        val intentToLaunch = MaterialEditActivity.createIntent(activity, karutaId)
        activity.startActivity(intentToLaunch)
    }

    fun navigateToKaruta(karutaId: KarutaIdentifier) {
        val intentToLaunch = KarutaActivity.createIntent(activity, karutaId)
        activity.startActivity(intentToLaunch)
    }

    fun navigateToAppStore() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(activity.getString(R.string.app_url)))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

    fun openLicenceDialog() {
        if (activity.supportFragmentManager.findFragmentByTag(LicenceDialogFragment.TAG) == null) {
            LicenceDialogFragment.newInstance().show(activity.supportFragmentManager, LicenceDialogFragment.TAG)
        }
    }

    fun back() {
        activity.finish()
    }
}
