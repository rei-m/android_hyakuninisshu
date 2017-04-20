package me.rei_m.hyakuninisshu.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.util.Unit;

public class KarutaModel {

    private final KarutaRepository karutaRepository;

    private PublishSubject<Karuta> completeGetKarutaEventSubject = PublishSubject.create();
    public Observable<Karuta> completeGetKarutaEvent = completeGetKarutaEventSubject;

    private PublishSubject<List<Karuta>> completeGetKarutaListEventSubject = PublishSubject.create();
    public Observable<List<Karuta>> completeGetKarutaListEvent = completeGetKarutaListEventSubject;

    private PublishSubject<Unit> errorSubject = PublishSubject.create();
    public Observable<Unit> error = errorSubject;

    public KarutaModel(@NonNull KarutaRepository karutaRepository) {
        this.karutaRepository = karutaRepository;
    }

    public void getKaruta(@NonNull KarutaIdentifier karutaIdentifier) {
        karutaRepository.resolve(karutaIdentifier)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karuta -> {
                    completeGetKarutaEventSubject.onNext(karuta);
                }, e -> {
                    errorSubject.onNext(Unit.INSTANCE);
                });
    }

    public void getKarutaList(@Nullable Color color) {
        karutaRepository.asEntityList(color != null ? color.getCode() : null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karuta -> {
                    completeGetKarutaListEventSubject.onNext(karuta);
                }, e -> {
                    errorSubject.onNext(Unit.INSTANCE);
                });
    }
}
