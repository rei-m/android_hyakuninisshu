package me.rei_m.hyakuninisshu.presentation.module;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;

@Module
public class ActivityModule {

    private final AppCompatActivity activity;

    public ActivityModule(@NonNull AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    Navigator provideNavigator() {
        return new Navigator(activity);
    }
}
