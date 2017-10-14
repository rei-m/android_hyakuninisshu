package me.rei_m.hyakuninisshu.infrastructure.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.model.karuta.BottomPhrase;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.TopPhrase;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;

public class KarutaRepositoryImpl implements KarutaRepository {

    private final Context context;

    private final SharedPreferences preferences;

    private final OrmaDatabase orma;

    public KarutaRepositoryImpl(@NonNull Context context,
                                @NonNull SharedPreferences preferences,
                                @NonNull OrmaDatabase orma) {
        this.preferences = preferences;
        this.context = context;
        this.orma = orma;
    }

    @Override
    public Completable initializeEntityList() {

        int karutaJsonVersion = preferences.getInt(KarutaJsonConstant.KEY_KARUTA_JSON_VERSION, 0);

        if (karutaJsonVersion < KarutaJsonConstant.KARUTA_VERSION) {
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

                return orma.transactionAsCompletable(() -> {

                    for (KarutaSchema karutaSchema : karutaSchemaList){
                        int count = KarutaSchema.relation(orma).selector().idEq(karutaSchema.id)
                                .and()
                                .where("isEdited = ?", true).count();
                        if (count == 0) {
                            KarutaSchema.relation(orma).deleter().idEq(karutaSchema.id).execute();
                            KarutaSchema.relation(orma).inserter().execute(karutaSchema);
                        }
                    }

                    preferences.edit()
                            .putInt(KarutaJsonConstant.KEY_KARUTA_JSON_VERSION, KarutaJsonConstant.KARUTA_VERSION)
                            .apply();
                });
            } catch (IOException e) {
                return Completable.error(e);
            }
        } else {
            return Completable.complete();
        }
    }

    @Override
    public Single<List<Karuta>> asEntityList() {
        return KarutaSchema.relation(orma).selector()
                .orderByIdAsc()
                .executeAsObservable()
                .map(KarutaFactory::create)
                .toList();
    }

    @Override
    public Single<List<Karuta>> asEntityList(@Nullable String color) {

        KarutaSchema_Selector selector = KarutaSchema.relation(orma).selector();

        if (color != null) {
            selector = selector.where("color = ?", color);
        }

        return selector
                .orderByIdAsc()
                .executeAsObservable()
                .map(KarutaFactory::create)
                .toList();
    }

    @Override
    public Single<List<Karuta>> asEntityList(@NonNull KarutaIdentifier fromIdentifier,
                                             @NonNull KarutaIdentifier toIdentifier,
                                             @Nullable String color) {

        KarutaSchema_Selector selector = KarutaSchema.relation(orma).selector()
                .idGe(fromIdentifier.value())
                .and()
                .idLe(toIdentifier.value());

        if (color != null) {
            selector = selector.where("color = ?", color);
        }

        return selector
                .orderByIdAsc()
                .executeAsObservable()
                .map(KarutaFactory::create)
                .toList();
    }

    @Override
    public Single<List<Karuta>> asEntityList(@NonNull KarutaIdentifier fromIdentifier,
                                             @NonNull KarutaIdentifier toIdentifier,
                                             @Nullable String color,
                                             int kimariji) {

        KarutaSchema_Selector selector = KarutaSchema.relation(orma).selector()
                .idGe(fromIdentifier.value())
                .and()
                .idLe(toIdentifier.value())
                .and()
                .where("kimariji = ?", kimariji);

        if (color != null) {
            selector = selector.and().where("color = ?", color);
        }

        return selector
                .orderByIdAsc()
                .executeAsObservable()
                .map(KarutaFactory::create)
                .toList();
    }

    @Override
    public Single<Karuta> resolve(@NonNull KarutaIdentifier identifier) {
        return KarutaSchema.relation(orma).selector()
                .idEq(identifier.value())
                .executeAsObservable()
                .map(KarutaFactory::create)
                .singleOrError();
    }

    @Override
    public Completable store(@NonNull Karuta karuta) {

        TopPhrase topPhrase = karuta.topPhrase();
        BottomPhrase bottomPhrase = karuta.bottomPhrase();

        return KarutaSchema.relation(orma).updater()
                .idEq(karuta.identifier().value())
                .firstKana(topPhrase.first().kana())
                .firstKanji(topPhrase.first().kanji())
                .secondKana(topPhrase.second().kana())
                .secondKanji(topPhrase.second().kanji())
                .thirdKana(topPhrase.third().kana())
                .thirdKanji(topPhrase.third().kanji())
                .fourthKana(bottomPhrase.fourth().kana())
                .fourthKanji(bottomPhrase.fourth().kanji())
                .fifthKana(bottomPhrase.fifth().kana())
                .fifthKanji(bottomPhrase.fifth().kanji())
                .isEdited(true)
                .executeAsSingle()
                .toCompletable();
    }
}
