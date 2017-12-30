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

package me.rei_m.hyakuninisshu.action;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.processors.PublishProcessor;

@Singleton
public class Dispatcher {

    @Inject
    public Dispatcher() {
    }

    private final PublishProcessor<Action> processor = PublishProcessor.create();

    public void dispatch(@NonNull Action action) {
        processor.onNext(action);
    }

    public <T extends Action> Observable<T> on(Class<T> clazz) {
        return processor.onBackpressureBuffer()
                .ofType(clazz)
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable();
    }
}
