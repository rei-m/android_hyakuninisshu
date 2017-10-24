/*
 * Copyright (c) 2017. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

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
import me.rei_m.hyakuninisshu.domain.model.karuta.Color;
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKu;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas;
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji;
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKu;

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
    public Completable initialize() {

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

                    for (KarutaSchema karutaSchema : karutaSchemaList) {
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
    public Single<Karutas> list() {
        return KarutaSchema.relation(orma).selector()
                .orderByIdAsc()
                .executeAsObservable()
                .map(KarutaFactory::create)
                .toList()
                .map(Karutas::new);
    }

    @Override
    public Single<KarutaIds> findIds(@NonNull KarutaIdentifier fromIdentifier,
                                     @NonNull KarutaIdentifier toIdentifier,
                                     @Nullable Color color,
                                     @Nullable Kimariji kimariji) {
        KarutaSchema_Selector selector = KarutaSchema.relation(orma).selector()
                .idGe(fromIdentifier.value())
                .and()
                .idLe(toIdentifier.value());

        if (color != null) {
            selector = selector.and().where("color = ?", color.value());
        }

        if (kimariji != null) {
            selector = selector.and().where("kimariji = ?", kimariji.value());
        }

        return selector
                .orderByIdAsc()
                .executeAsObservable()
                .map(karutaSchema -> new KarutaIdentifier((int) karutaSchema.id))
                .toList()
                .map(KarutaIds::new);
    }

    @Override
    public Single<KarutaIds> findIds() {
        return KarutaSchema.relation(orma).selector()
                .orderByIdAsc()
                .executeAsObservable()
                .map(karutaSchema -> new KarutaIdentifier((int) karutaSchema.id))
                .toList()
                .map(KarutaIds::new);
    }

    @Override
    public Single<Karuta> findBy(@NonNull KarutaIdentifier identifier) {
        return KarutaSchema.relation(orma).selector()
                .idEq(identifier.value())
                .executeAsObservable()
                .map(KarutaFactory::create)
                .singleOrError();
    }

    @Override
    public Completable store(@NonNull Karuta karuta) {

        KamiNoKu kamiNoKu = karuta.kamiNoKu();
        ShimoNoKu shimoNoKu = karuta.shimoNoKu();

        return KarutaSchema.relation(orma).updater()
                .idEq(karuta.identifier().value())
                .firstKana(kamiNoKu.first().kana())
                .firstKanji(kamiNoKu.first().kanji())
                .secondKana(kamiNoKu.second().kana())
                .secondKanji(kamiNoKu.second().kanji())
                .thirdKana(kamiNoKu.third().kana())
                .thirdKanji(kamiNoKu.third().kanji())
                .fourthKana(shimoNoKu.fourth().kana())
                .fourthKanji(shimoNoKu.fourth().kanji())
                .fifthKana(shimoNoKu.fifth().kana())
                .fifthKanji(shimoNoKu.fifth().kanji())
                .isEdited(true)
                .executeAsSingle()
                .toCompletable();
    }
}
