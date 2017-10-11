package me.rei_m.hyakuninisshu.model;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.util.Unit;

@Singleton
public class ApplicationModel {

    private PublishSubject<Unit> completeStartEventSubject = PublishSubject.create();
    public Observable<Unit> completeStartEvent = completeStartEventSubject;

    private final KarutaRepository karutaRepository;

    @Inject
    public ApplicationModel(@NonNull KarutaRepository karutaRepository) {
        this.karutaRepository = karutaRepository;
    }

    public void start() {
        karutaRepository.initializeEntityList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    completeStartEventSubject.onNext(Unit.INSTANCE);
                });
    }
}
