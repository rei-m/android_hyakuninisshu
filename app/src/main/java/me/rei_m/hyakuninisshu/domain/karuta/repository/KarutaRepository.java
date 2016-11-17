package me.rei_m.hyakuninisshu.domain.karuta.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;

public interface KarutaRepository {

    Completable initializeEntityList();

    Single<List<Karuta>> asEntityList();

    Single<List<Karuta>> asEntityList(KarutaIdentifier fromIdentifier, KarutaIdentifier toIdentifier);

    Single<List<Karuta>> asEntityList(KarutaIdentifier fromIdentifier, KarutaIdentifier toIdentifier, int kimariji);

    Maybe<Karuta> resolve(KarutaIdentifier identifier);
}
