package me.rei_m.hyakuninisshu.domain.karuta.repository.impl;

import android.support.annotation.NonNull;

import java.util.List;

import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;
import rx.Observable;

public class KarutaRepositoryImpl implements KarutaRepository {

    private OrmaDatabase orma;

    public KarutaRepositoryImpl(@NonNull OrmaDatabase orma) {
        this.orma = orma;
    }

    @Override
    public Observable<List<Karuta>> asEntityList() {
        return KarutaSchema.relation(orma).selector()
                .executeAsObservable()
                .map(KarutaFactory::create)
                .toList();
    }
}
