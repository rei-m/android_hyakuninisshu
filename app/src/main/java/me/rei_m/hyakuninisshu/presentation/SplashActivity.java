package me.rei_m.hyakuninisshu.presentation;

import android.app.Activity;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.Binds;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.multibindings.IntoMap;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.model.ApplicationModel;
import me.rei_m.hyakuninisshu.presentation.di.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;

public class SplashActivity extends DaggerAppCompatActivity {

    @ForActivity
    @dagger.Subcomponent(modules = {ActivityModule.class})
    public interface Subcomponent extends AndroidInjector<SplashActivity> {

        @dagger.Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<SplashActivity> {

            public abstract Builder activityModule(ActivityModule module);

            @Override
            public void seedInstance(SplashActivity instance) {
                activityModule(new ActivityModule(instance));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @Binds
        @IntoMap
        @ActivityKey(SplashActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bind(Subcomponent.Builder builder);
    }

    @Inject
    Navigator navigator;

    @Inject
    ApplicationModel applicationModel;

    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        disposable = new CompositeDisposable();
        disposable.add(applicationModel.completeStartEvent.subscribe(v -> {
            navigator.navigateToEntrance();
            finish();
        }));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        applicationModel.start();
    }
}
