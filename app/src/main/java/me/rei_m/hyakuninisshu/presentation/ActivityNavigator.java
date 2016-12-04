package me.rei_m.hyakuninisshu.presentation;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.karuta.ExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;

public class ActivityNavigator {

    public void navigateToEntrance(@NonNull Activity activity) {
        Intent intentToLaunch = EntranceActivity.createIntent(activity);
        intentToLaunch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToQuizMaster(@NonNull Activity activity,
                                     @NonNull TrainingRange trainingRange,
                                     @NonNull Kimariji kimariji,
                                     @NonNull KarutaStyle topPhraseStyle,
                                     @NonNull KarutaStyle bottomPhraseStyle) {
        Intent intentToLaunch = QuizMasterActivity.createIntent(activity,
                trainingRange,
                kimariji,
                topPhraseStyle,
                bottomPhraseStyle);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToExamMaster(@NonNull Activity activity) {
        Intent intentToLaunch = ExamMasterActivity.createIntent(activity);
        activity.startActivity(intentToLaunch);
    }
}
