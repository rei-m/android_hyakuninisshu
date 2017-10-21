package me.rei_m.hyakuninisshu;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Singleton;

import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;
import io.fabric.sdk.android.Fabric;
import me.rei_m.hyakuninisshu.di.ApplicationModule;
import me.rei_m.hyakuninisshu.infrastructure.di.InfrastructureModule;
import me.rei_m.hyakuninisshu.presentation.EntranceActivity;
import me.rei_m.hyakuninisshu.presentation.SplashActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.ExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialDetailActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialEditActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialSingleActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMasterActivity;

public class App extends DaggerApplication {

    @Singleton
    @dagger.Component(modules = {
            AndroidSupportInjectionModule.class,
            ApplicationModule.class,
            InfrastructureModule.class,
            SplashActivity.Module.class,
            EntranceActivity.Module.class,
            ExamMasterActivity.Module.class,
            MaterialDetailActivity.Module.class,
            MaterialEditActivity.Module.class,
            MaterialSingleActivity.Module.class,
            TrainingExamMasterActivity.Module.class,
            TrainingMasterActivity.Module.class
    })
    interface Component extends AndroidInjector<App> {
    }

    @Override
    public void onCreate() {
        super.onCreate();

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
        return DaggerApp_Component.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
