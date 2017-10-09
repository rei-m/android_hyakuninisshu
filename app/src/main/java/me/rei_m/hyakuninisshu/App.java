package me.rei_m.hyakuninisshu;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.fabric.sdk.android.Fabric;
import me.rei_m.hyakuninisshu.component.DaggerApplicationComponent;
import me.rei_m.hyakuninisshu.domain.karuta.module.KarutaDomainModule;
import me.rei_m.hyakuninisshu.infrastructure.module.InfrastructureModule;
import me.rei_m.hyakuninisshu.module.ApplicationModule;

public class App extends MultiDexApplication implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .infrastructureModule(new InfrastructureModule())
                .karutaDomainModule(new KarutaDomainModule())
                .build()
                .inject(this);

        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }

        CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, new Crashlytics.Builder().core(crashlyticsCore).build());
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
