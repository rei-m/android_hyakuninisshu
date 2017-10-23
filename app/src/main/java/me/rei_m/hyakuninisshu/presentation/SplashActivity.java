/*
 * Copyright (c) 2017. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

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
    protected void onDestroy() {
        navigator = null;
        applicationModel = null;
        super.onDestroy();
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
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        applicationModel.start();
    }
}
