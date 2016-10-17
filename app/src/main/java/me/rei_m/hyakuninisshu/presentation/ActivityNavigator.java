package me.rei_m.hyakuninisshu.presentation;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.karuta.EntranceActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizMasterActivity;

public class ActivityNavigator {

    public void navigateToEntrance(@NonNull Activity activity) {
        Intent intentToLaunch = EntranceActivity.createIntent(activity);
        intentToLaunch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intentToLaunch);
    }

    public void navigateToQuizMaster(@NonNull Activity activity) {
        Intent intentToLaunch = QuizMasterActivity.createIntent(activity);
        activity.startActivity(intentToLaunch);
    }
}
