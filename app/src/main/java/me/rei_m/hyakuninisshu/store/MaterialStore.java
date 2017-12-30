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

package me.rei_m.hyakuninisshu.store;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.rei_m.hyakuninisshu.action.Dispatcher;
import me.rei_m.hyakuninisshu.action.karuta.EditKarutaAction;
import me.rei_m.hyakuninisshu.action.material.FetchMaterialAction;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;

@Singleton
public class MaterialStore {

    private final BehaviorSubject<List<Karuta>> karutaListSubject = BehaviorSubject.create();
    public final Observable<List<Karuta>> karutaList = karutaListSubject;

    public final Observable<Karuta> karuta(@NonNull KarutaIdentifier karutaId) {
        return karutaListSubject.flatMap(value -> Observable.fromIterable(value).filter(karuta -> karuta.identifier().equals(karutaId)));
    }

    @Inject
    public MaterialStore(@NonNull Dispatcher dispatcher) {
        dispatcher.on(FetchMaterialAction.class).subscribe(action -> {
            karutaListSubject.onNext(action.karutas.asList());
        });
        dispatcher.on(EditKarutaAction.class).subscribe(action -> {
            if (karutaListSubject.hasValue()) {
                List<Karuta> karutaList = new ArrayList<>(karutaListSubject.getValue());
                karutaList.set(karutaList.indexOf(action.karuta), action.karuta);
                karutaListSubject.onNext(Collections.unmodifiableList(karutaList));
            }
        });
    }
}
