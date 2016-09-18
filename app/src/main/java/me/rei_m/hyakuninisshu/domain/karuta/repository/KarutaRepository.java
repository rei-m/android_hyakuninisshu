package me.rei_m.hyakuninisshu.domain.karuta.repository;

import java.util.List;

import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import rx.Observable;

public interface KarutaRepository {

    Observable<List<Karuta>> asEntityList();

}
