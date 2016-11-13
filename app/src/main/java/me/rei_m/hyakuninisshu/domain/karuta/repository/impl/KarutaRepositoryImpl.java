package me.rei_m.hyakuninisshu.domain.karuta.repository.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.gfx.android.orma.Inserter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaFactory;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaJsonAdaptor;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;

public class KarutaRepositoryImpl implements KarutaRepository {

    private Context context;

    private OrmaDatabase orma;

    public KarutaRepositoryImpl(@NonNull Context context, @NonNull OrmaDatabase orma) {
        this.context = context;
        this.orma = orma;
    }

    @Override
    public Observable<Void> initializeEntityList() {

        try {
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
                Inserter<KarutaSchema> inserter = KarutaSchema.relation(orma).inserter();
                inserter.executeAll(karutaSchemaList);
            }).toObservable();

        } catch (IOException e) {
            return Observable.error(e);
        }
    }

    @Override
    public Observable<List<Karuta>> asEntityList() {
        return KarutaSchema.relation(orma).selector()
                .orderByIdAsc()
                .executeAsObservable()
                .map(KarutaFactory::create)
                .toList();
    }

    @Override
    public Observable<List<Karuta>> asEntityList(KarutaIdentifier fromIdentifier, KarutaIdentifier toIdentifier) {
        return KarutaSchema.relation(orma).selector()
                .idGe(fromIdentifier.getValue())
                .and()
                .idLe(toIdentifier.getValue())
                .orderByIdAsc()
                .executeAsObservable()
                .map(KarutaFactory::create)
                .toList();
    }

    @Override
    public Observable<List<Karuta>> asEntityList(KarutaIdentifier fromIdentifier, KarutaIdentifier toIdentifier, int kimariji) {
        return KarutaSchema.relation(orma).selector()
                .idGe(fromIdentifier.getValue())
                .and()
                .idLe(toIdentifier.getValue())
                .and()
                .where("kimariji = ?", kimariji)
                .orderByIdAsc()
                .executeAsObservable()
                .map(KarutaFactory::create)
                .toList();
    }

    @Override
    public Observable<Karuta> resolve(KarutaIdentifier identifier) {
        return KarutaSchema.relation(orma).selector()
                .idEq(identifier.getValue())
                .executeAsObservable()
                .map(KarutaFactory::create);
    }
}
