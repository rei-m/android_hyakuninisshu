package me.rei_m.hyakuninisshu;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import me.rei_m.hyakuninisshu.di.ApplicationComponent;
import me.rei_m.hyakuninisshu.di.ApplicationModule;
import me.rei_m.hyakuninisshu.di.DaggerApplicationComponent;
import me.rei_m.hyakuninisshu.di.DomainModule;
import me.rei_m.hyakuninisshu.di.InfrastructureModule;
import me.rei_m.hyakuninisshu.di.UsecaseModule;

public class App extends Application {

    private ApplicationComponent component;

    public ApplicationComponent getComponent() {
        return component;
    }
    
    @VisibleForTesting
    protected ApplicationComponent createApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .infrastructureModule(new InfrastructureModule())
                .domainModule(new DomainModule())
                .usecaseModule(new UsecaseModule())
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.component = createApplicationComponent();
        this.component.inject(this);
    }
}
