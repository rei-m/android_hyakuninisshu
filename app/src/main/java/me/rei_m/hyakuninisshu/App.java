package me.rei_m.hyakuninisshu;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import me.rei_m.hyakuninisshu.component.ApplicationComponent;
import me.rei_m.hyakuninisshu.component.DaggerApplicationComponent;
import me.rei_m.hyakuninisshu.module.ApplicationModule;

public class App extends Application {

    private ApplicationComponent component;

    public ApplicationComponent getComponent() {
        return component;
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
        this.component = createApplicationComponent();
        this.component.inject(this);
    }
}
