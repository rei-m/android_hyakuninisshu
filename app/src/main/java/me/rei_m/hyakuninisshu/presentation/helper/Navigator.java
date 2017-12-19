/*
 * Copyright (c) 2017. Rei Matsushita
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

package me.rei_m.hyakuninisshu.presentation.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.presentation.EntranceActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.ExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialDetailActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialEditActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialSingleActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeTo;

public class Navigator {

    private final Activity activity;

    public Navigator(@NonNull Activity activity) {
        this.activity = activity;
    }

    public void navigateToEntrance() {
        Intent intentToLaunch = EntranceActivity.createIntent(activity);
        intentToLaunch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToTrainingMaster(@NonNull TrainingRangeFrom trainingRangeFrom,
                                         @NonNull TrainingRangeTo trainingRangeTo,
                                         @NonNull KimarijiFilter kimarijiFilter,
                                         @NonNull ColorFilter colorFilter,
                                         @NonNull KarutaStyleFilter kamiNoKuStyle,
                                         @NonNull KarutaStyleFilter shimoNoKuStyle) {
        Intent intentToLaunch = TrainingMasterActivity.createIntent(activity,
                trainingRangeFrom,
                trainingRangeTo,
                kimarijiFilter,
                colorFilter,
                kamiNoKuStyle,
                shimoNoKuStyle);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToExamMaster() {
        Intent intentToLaunch = ExamMasterActivity.createIntent(activity);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToExamTrainingMaster() {
        Intent intentToLaunch = TrainingExamMasterActivity.createIntent(activity);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToMaterialDetail(int position,
                                         @NonNull ColorFilter colorFilter) {
        Intent intentToLaunch = MaterialDetailActivity.createIntent(activity, position, colorFilter);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToMaterialSingle(@NonNull KarutaIdentifier karutaId) {
        Intent intentToLaunch = MaterialSingleActivity.createIntent(activity, karutaId);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToMaterialEdit(@NonNull KarutaIdentifier karutaId) {
        Intent intentToLaunch = MaterialEditActivity.createIntent(activity, karutaId);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToAppStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(activity.getString(R.string.app_url)));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
