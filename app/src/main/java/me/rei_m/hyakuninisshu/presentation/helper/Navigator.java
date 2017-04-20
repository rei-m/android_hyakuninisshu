package me.rei_m.hyakuninisshu.presentation.helper;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.EntranceActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.ExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialDetailActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
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
                                         @NonNull Kimariji kimariji,
                                         @NonNull Color color,
                                         @NonNull KarutaStyle topPhraseStyle,
                                         @NonNull KarutaStyle bottomPhraseStyle) {
        Intent intentToLaunch = TrainingMasterActivity.createIntent(activity,
                trainingRangeFrom,
                trainingRangeTo,
                kimariji,
                color,
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
}
