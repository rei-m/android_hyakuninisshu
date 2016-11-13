package me.rei_m.hyakuninisshu.presentation.karuta;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
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

        karutaRepository.asEntityList().concatMap(new Function<List<Karuta>, Observable<Void>>() {
            @Override
            public Observable<Void> apply(List<Karuta> karutaList) throws Exception {
                if (karutaList.isEmpty()) {
                    return karutaRepository.initializeEntityList();
                } else {
                    return Observable.empty();
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(karutaList -> {
            activityNavigator.navigateToEntrance(this);
        });
    }

    @Override
    protected void setupActivityComponent() {
        ((App) getApplication()).getComponent().plus(new SplashActivityModule(this)).inject(this);
    }
}
