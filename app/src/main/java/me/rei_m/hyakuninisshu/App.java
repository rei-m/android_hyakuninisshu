package me.rei_m.hyakuninisshu;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.squareup.leakcanary.LeakCanary;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.fabric.sdk.android.Fabric;
import me.rei_m.hyakuninisshu.component.DaggerApplicationComponent;
import me.rei_m.hyakuninisshu.module.ApplicationModule;

public class App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }

        CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, new Crashlytics.Builder().core(crashlyticsCore).build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected AndroidInjector<App> applicationInjector() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
