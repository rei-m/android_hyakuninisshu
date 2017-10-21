package me.rei_m.hyakuninisshu.presentation.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.presentation.EntranceActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.ExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialDetailActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialEditActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialSingleActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeTo;

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
                                         @NonNull KarutaStyleFilter topPhraseStyle,
                                         @NonNull KarutaStyleFilter bottomPhraseStyle) {
        Intent intentToLaunch = TrainingMasterActivity.createIntent(activity,
                trainingRangeFrom,
                trainingRangeTo,
                kimarijiFilter,
                colorFilter,
                topPhraseStyle,
                bottomPhraseStyle);
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

    public void navigateToMaterialDetail(int karutaNo) {
        Intent intentToLaunch = MaterialDetailActivity.createIntent(activity, karutaNo);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToMaterialSingle(int karutaNo) {
        Intent intentToLaunch = MaterialSingleActivity.createIntent(activity, karutaNo);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToMaterialEdit(int karutaNo) {
        Intent intentToLaunch = MaterialEditActivity.createIntent(activity, karutaNo);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToAppStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(activity.getString(R.string.app_url)));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
