package me.rei_m.hyakuninisshu.presentation.karuta;

import android.os.Bundle;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.BaseActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.module.SplashActivityModule;

public class SplashActivity extends BaseActivity {

    @Inject
    ActivityNavigator activityNavigator;

    @Inject
    KarutaRepository karutaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        karutaRepository.asEntityList().flatMapCompletable(karutaList -> {
            if (karutaList.isEmpty()) {
                return karutaRepository.initializeEntityList();
            } else {
                return Completable.complete();
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
            activityNavigator.navigateToEntrance(this);
        });
    }

    @Override
    protected void setupActivityComponent() {
        ((App) getApplication()).getComponent().plus(new SplashActivityModule(this)).inject(this);
    }
}
