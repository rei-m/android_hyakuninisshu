package me.rei_m.hyakuninisshu.domain.karuta.repository;

import java.io.IOException;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import rx.Completable;
import rx.Observable;

public interface KarutaRepository {

    Observable<Void> initializeEntityList();

    Observable<List<Karuta>> asEntityList();
}
