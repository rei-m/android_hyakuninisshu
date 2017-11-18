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

package me.rei_m.hyakuninisshu.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.domain.model.karuta.Color;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.util.EventObservable;
import me.rei_m.hyakuninisshu.util.Unit;

@Singleton
public class KarutaModel {

    public final EventObservable<Karuta> karuta = EventObservable.create();

    public final EventObservable<Unit> editedEvent = EventObservable.create();

    public final EventObservable<Unit> errorEvent = EventObservable.create();

    private final KarutaRepository karutaRepository;

    @Inject
    public KarutaModel(@NonNull KarutaRepository karutaRepository) {
        this.karutaRepository = karutaRepository;
    }

    public void fetchKaruta(@NonNull KarutaIdentifier karutaIdentifier) {
        karutaRepository.findBy(karutaIdentifier)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karuta::onNext, e -> errorEvent.onNext(Unit.INSTANCE));
    }

    public void editKaruta(@NonNull KarutaIdentifier karutaIdentifier,
                           @NonNull String firstPhraseKanji,
                           @NonNull String firstPhraseKana,
                           @NonNull String secondPhraseKanji,
                           @NonNull String secondPhraseKana,
                           @NonNull String thirdPhraseKanji,
                           @NonNull String thirdPhraseKana,
                           @NonNull String fourthPhraseKanji,
                           @NonNull String fourthPhraseKana,
                           @NonNull String fifthPhraseKanji,
                           @NonNull String fifthPhraseKana) {
        Single<Karuta> karutaSingle = karutaRepository.findBy(karutaIdentifier).map(karuta ->
                karuta.updatePhrase(
                        firstPhraseKanji,
                        firstPhraseKana,
                        secondPhraseKanji,
                        secondPhraseKana,
                        thirdPhraseKanji,
                        thirdPhraseKana,
                        fourthPhraseKanji,
                        fourthPhraseKana,
                        fifthPhraseKanji,
                        fifthPhraseKana
                )
        );
        karutaSingle.flatMap(karuta -> karutaRepository.store(karuta).andThen(Single.just(karuta)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karuta -> {
                    this.karuta.onNext(karuta);
                    this.editedEvent.onNext(Unit.INSTANCE);
                }, e -> errorEvent.onNext(Unit.INSTANCE));
    }

    public void fetchKarutas(@Nullable Color color) {
        karutaRepository.list()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karutas -> {
                    for (Karuta karuta : karutas.asList(color)) {
                        this.karuta.onNext(karuta);
                    }
                }, e -> errorEvent.onNext(Unit.INSTANCE));
    }
}
