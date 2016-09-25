package me.rei_m.hyakuninisshu.presentation.karuta;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.presentation.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    @Inject
    KarutaRepository karutaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getComponent().inject(this);

        karutaRepository.asEntityList().concatMap(new Func1<List<Karuta>, Observable<Void>>() {
            @Override
            public Observable<Void> call(List<Karuta> karutaList) {
                if (karutaList.isEmpty()) {
                    return karutaRepository.initializeEntityList();
                } else {
                    return Observable.just(null);
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(karutaList -> {

        });
    }
}
