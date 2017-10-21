package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface KarutaRepository {

    Completable initialize();

    Single<Karuta> findBy(@NonNull KarutaIdentifier identifier);

    Single<Karutas> list();

    Single<KarutaIds> findIds();

    Single<KarutaIds> findIds(@NonNull KarutaIdentifier fromIdentifier,
                              @NonNull KarutaIdentifier toIdentifier,
                              @Nullable Color color,
                              @Nullable Kimariji kimariji);

    Completable store(@NonNull Karuta karuta);
}
