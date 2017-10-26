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

package me.rei_m.hyakuninisshu.util;

import io.reactivex.Observable;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public final class EventObservable<T> {

    private final PublishSubject<T> publishSubject;

    private final Observable<T> observable;

    private EventObservable() {
        this.publishSubject = PublishSubject.create();
        this.observable = this.publishSubject;
    }

    @CheckReturnValue
    public static <T> EventObservable<T> create() {
        return new EventObservable<>();
    }

    public void onNext(T t) {
        publishSubject.onNext(t);
    }

    public Disposable subscribe(Consumer<? super T> onNext) {
        return observable.subscribe(onNext);
    }
}
