package me.rei_m.hyakuninisshu.presentation.karuta;

import android.os.Bundle;

import java.io.IOException;
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
        setContentView(R.layout.activity_entrance);

        getComponent().inject(this);

        karutaRepository.asEntityList().concatMap(new Func1<List<Karuta>, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(List<Karuta> karutaList) {
                if (karutaList.isEmpty()) {
                    try {
                        return karutaRepository.initializeEntityList(getApplicationContext())
                                .toObservable()
                                .map(v -> true);
                    } catch (IOException e) {
                        return Observable.error(e);
                    }
                }
                return Observable.just(false);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(karutaList -> {

        });
    }
}
