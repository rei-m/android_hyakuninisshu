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

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.action.Dispatcher;
import me.rei_m.hyakuninisshu.action.karuta.EditKarutaAction;
import me.rei_m.hyakuninisshu.action.karuta.FetchKarutaAction;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.util.Unit;

@ForActivity
public class KarutaStore {

    private final PublishSubject<Karuta> karutaSubject = PublishSubject.create();
    public final Observable<Karuta> karuta = karutaSubject;

    private final PublishSubject<Unit> editedEventSubject = PublishSubject.create();
    public final Observable<Unit> editedEvent = editedEventSubject;

    private final PublishSubject<Unit> errorSubject = PublishSubject.create();
    public final Observable<Unit> error = errorSubject;

    @Inject
    public KarutaStore(@NonNull Dispatcher dispatcher) {
        dispatcher.on(FetchKarutaAction.class).subscribe(action -> {
            karutaSubject.onNext(action.karuta);
        });
        dispatcher.on(EditKarutaAction.class).subscribe(action -> {
            if (action.error) {
                errorSubject.onNext(Unit.INSTANCE);
            } else {
                editedEventSubject.onNext(Unit.INSTANCE);
            }
        });
    }
}
