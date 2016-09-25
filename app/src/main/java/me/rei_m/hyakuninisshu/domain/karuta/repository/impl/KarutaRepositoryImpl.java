package me.rei_m.hyakuninisshu.domain.karuta.repository.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.gfx.android.orma.Inserter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaJsonAdaptor;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;
import rx.Completable;
import rx.Observable;

public class KarutaRepositoryImpl implements KarutaRepository {

    private OrmaDatabase orma;

    public KarutaRepositoryImpl(@NonNull OrmaDatabase orma) {
        this.orma = orma;
    }

    @Override
    public Completable initializeEntityList(Context context) throws IOException {

        InputStream inputStream = context.getAssets().open("karuta_list.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();

        List<KarutaSchema> karutaSchemaList = KarutaJsonAdaptor.convert(stringBuilder.toString());

        return orma.transactionAsync(() -> {
            Inserter<KarutaSchema> inserter = orma.prepareInsertIntoKarutaSchema();
            inserter.executeAll(karutaSchemaList);
        });
    }

    @Override
    public Observable<List<Karuta>> asEntityList() {
        return KarutaSchema.relation(orma).selector()
                .executeAsObservable()
                .map(KarutaFactory::create)
                .toList();
    }
}
