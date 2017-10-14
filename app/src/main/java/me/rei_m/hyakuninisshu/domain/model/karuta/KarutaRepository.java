package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;

public interface KarutaRepository {

    Completable initializeEntityList();

    Single<List<Karuta>> asEntityList();

    Single<List<Karuta>> asEntityList(@Nullable String color);

    Single<List<Karuta>> asEntityList(@NonNull KarutaIdentifier fromIdentifier,
                                      @NonNull KarutaIdentifier toIdentifier,
                                      @Nullable String color);

    Single<List<Karuta>> asEntityList(@NonNull KarutaIdentifier fromIdentifier,
                                      @NonNull KarutaIdentifier toIdentifier,
                                      @Nullable String color,
                                      int kimariji);

    Single<Karuta> resolve(@NonNull KarutaIdentifier identifier);

    Completable store(@NonNull Karuta karuta);
}
