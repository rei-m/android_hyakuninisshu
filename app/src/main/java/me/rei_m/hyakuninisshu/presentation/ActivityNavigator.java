package me.rei_m.hyakuninisshu.presentation;

import android.app.Activity;
import android.content.Intent;

import me.rei_m.hyakuninisshu.presentation.karuta.EntranceActivity;

public class ActivityNavigator {

    public void navigateToEntrance(Activity activity) {
        Intent intentToLaunch = EntranceActivity.createIntent(activity);
        intentToLaunch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intentToLaunch);
    }

}
