package me.rei_m.hyakuninisshu.presentation;

import android.os.Bundle;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.presentation.module.SplashActivityModule;
import me.rei_m.hyakuninisshu.usecase.StartApplicationUsecase;

public class SplashActivity extends BaseActivity {

    @Inject
    ActivityNavigator navigator;

    @Inject
    StartApplicationUsecase startApplicationUsecase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startApplicationUsecase.execute()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    navigator.navigateToEntrance(this);
                    finish();
                });
    }

    @Override
    protected void setupActivityComponent() {
        ((App) getApplication()).getComponent().plus(new SplashActivityModule(this)).inject(this);
    }
}
