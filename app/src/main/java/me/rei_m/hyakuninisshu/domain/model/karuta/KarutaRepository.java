package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface KarutaRepository {

    Completable initialize();

    Single<Karutas> findAll();

    Single<KarutaIds> findForTraining(@NonNull KarutaIdentifier fromIdentifier,
                                      @NonNull KarutaIdentifier toIdentifier,
                                      @Nullable String color);

    Single<KarutaIds> findForTraining(@NonNull KarutaIdentifier fromIdentifier,
                                      @NonNull KarutaIdentifier toIdentifier,
                                      @Nullable String color,
                                      @NonNull Kimariji kimariji);

    Single<KarutaIds> findForExam();

    Single<Karuta> findBy(@NonNull KarutaIdentifier identifier);

    Completable store(@NonNull Karuta karuta);
}
