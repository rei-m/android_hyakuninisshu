package me.rei_m.hyakuninisshu;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
import me.rei_m.hyakuninisshu.component.ApplicationComponent;
import me.rei_m.hyakuninisshu.component.DaggerApplicationComponent;
import me.rei_m.hyakuninisshu.module.ApplicationModule;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;

public class App extends Application {

    private ApplicationComponent component;

    private AnalyticsManager analyticsManager;

    public ApplicationComponent getComponent() {
        return component;
    }

    public AnalyticsManager getAnalyticsManager() {
        return analyticsManager;
    }

    @VisibleForTesting
    protected ApplicationComponent createApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }

        CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, new Crashlytics.Builder().core(crashlyticsCore).build());

        analyticsManager = new AnalyticsManager();
        analyticsManager.initialize(this);

        this.component = createApplicationComponent();
        this.component.inject(this);
    }
}
