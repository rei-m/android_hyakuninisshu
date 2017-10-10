package me.rei_m.hyakuninisshu.presentation;

import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.model.ApplicationModel;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;

public class SplashActivity extends DaggerAppCompatActivity {

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
