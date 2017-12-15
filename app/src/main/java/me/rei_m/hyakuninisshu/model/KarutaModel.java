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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.model.karuta.Color;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.util.Unit;

/**
 * 歌モデル.
 */
@Singleton
public class KarutaModel {

    private final BehaviorSubject<List<Karuta>> karutaListSubject = BehaviorSubject.create();
    public final Observable<List<Karuta>> karutaList = karutaListSubject;

    private final PublishSubject<Unit> editedEventSubject = PublishSubject.create();
    public final Observable<Unit> editedEvent = editedEventSubject;

    private final PublishSubject<Unit> errorEventSubject = PublishSubject.create();
    public final Observable<Unit> errorEvent = errorEventSubject;

    private final KarutaRepository karutaRepository;

    private Color color;

    @Inject
    public KarutaModel(@NonNull KarutaRepository karutaRepository) {
        this.karutaRepository = karutaRepository;
    }

    /**
     * 歌コレクションを取得する.
     *
     * @param color 歌の色。指定された場合は色でしぼりこんだ歌を取得する
     */
    public void fetchKarutas(@Nullable Color color) {
        karutaRepository.list().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(karutas -> {
            karutaListSubject.onNext(karutas.asList(color));
            this.color = color;
        });
    }

    /**
     * 歌を編集する.
     *
     * @param karutaIdentifier  歌ID
     * @param firstPhraseKanji  初句の漢字
     * @param firstPhraseKana   初句のかな
     * @param secondPhraseKanji 二句の漢字
     * @param secondPhraseKana  二句のかな
     * @param thirdPhraseKanji  三句の漢字
     * @param thirdPhraseKana   三句のかな
     * @param fourthPhraseKanji 四句の漢字
     * @param fourthPhraseKana  四句のかな
     * @param fifthPhraseKanji  五句の漢字
     * @param fifthPhraseKana   五句のかな
     */
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
        karutaSingle.flatMapCompletable(karutaRepository::store).andThen(karutaRepository.list())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karutas -> {
                    karutaListSubject.onNext(karutas.asList(color));
                    editedEventSubject.onNext(Unit.INSTANCE);
                }, e -> errorEventSubject.onNext(Unit.INSTANCE));
    }
}
