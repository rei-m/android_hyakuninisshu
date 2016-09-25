package me.rei_m.hyakuninisshu.domain.karuta.repository;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import rx.Completable;
import rx.Observable;

public interface KarutaRepository {

    Completable initializeEntityList(Context context) throws IOException;

    Observable<List<Karuta>> asEntityList();
}
