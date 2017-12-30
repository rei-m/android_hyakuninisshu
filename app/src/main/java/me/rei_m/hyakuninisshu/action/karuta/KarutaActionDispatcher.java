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

package me.rei_m.hyakuninisshu.action.karuta;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.action.Dispatcher;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;

public class KarutaActionDispatcher {

    private final Dispatcher dispatcher;
    private final KarutaRepository karutaRepository;

    @Inject
    public KarutaActionDispatcher(@NonNull Dispatcher dispatcher,
                                  @NonNull KarutaRepository karutaRepository) {
        this.dispatcher = dispatcher;
        this.karutaRepository = karutaRepository;
    }

    public void fetch(@NonNull KarutaIdentifier karutaIdentifier) {
        karutaRepository.findBy(karutaIdentifier).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(karuta -> {
            dispatcher.dispatch(new FetchKarutaAction(karuta));
        });
    }

    public void edit(@NonNull KarutaIdentifier karutaIdentifier,
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

        karutaRepository.findBy(karutaIdentifier).flatMap(karuta -> {
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
            );
            return karutaRepository.store(karuta).andThen(Single.just(karuta));
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(karuta -> {
            dispatcher.dispatch(new EditKarutaAction(karuta));
        });
    }
}
