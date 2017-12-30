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
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.action.Dispatcher;
import me.rei_m.hyakuninisshu.action.training.AggregateResultsAction;
import me.rei_m.hyakuninisshu.action.training.StartTrainingAction;
import me.rei_m.hyakuninisshu.domain.model.quiz.TrainingResult;
import me.rei_m.hyakuninisshu.util.Unit;

@Singleton
public class TrainingStore {

    private final PublishSubject<Unit> startedEventSubject = PublishSubject.create();
    public final Observable<Unit> startedEvent = startedEventSubject;

    private final PublishSubject<TrainingResult> resultSubject = PublishSubject.create();
    public final Observable<TrainingResult> result = resultSubject;

    private final PublishSubject<Unit> notFoundErrorEventSubject = PublishSubject.create();
    public final Observable<Unit> notFoundErrorEvent = notFoundErrorEventSubject;

    @Inject
    public TrainingStore(@NonNull Dispatcher dispatcher) {
        dispatcher.on(StartTrainingAction.class).subscribe(action -> {
            if (action.error) {
                notFoundErrorEventSubject.onNext(Unit.INSTANCE);
            } else {
                startedEventSubject.onNext(Unit.INSTANCE);
            }
        });
        dispatcher.on(AggregateResultsAction.class).subscribe(action -> {
            if (action.error) {
                notFoundErrorEventSubject.onNext(Unit.INSTANCE);
            } else {
                resultSubject.onNext(action.trainingResult);
            }
        });
    }
}
