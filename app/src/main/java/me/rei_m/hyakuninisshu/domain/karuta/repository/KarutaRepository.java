package me.rei_m.hyakuninisshu.domain.karuta.repository;

import java.util.List;

import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import rx.Observable;

public interface KarutaRepository {

    Observable<Void> initializeEntityList();

    Observable<List<Karuta>> asEntityList();

    Observable<Karuta> resolve(KarutaIdentifier identifier);
}
