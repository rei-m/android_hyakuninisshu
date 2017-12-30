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
import me.rei_m.hyakuninisshu.action.exam.FetchExamAction;
import me.rei_m.hyakuninisshu.action.exam.FetchRecentExamAction;
import me.rei_m.hyakuninisshu.action.exam.FinishExamAction;
import me.rei_m.hyakuninisshu.action.exam.StartExamAction;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.util.Unit;

@Singleton
public class ExamStore {

    private final PublishSubject<KarutaExam> karutaExamSubject = PublishSubject.create();
    public final Observable<KarutaExam> karutaExam = karutaExamSubject;

    private final PublishSubject<KarutaExam> recentKarutaExamSubject = PublishSubject.create();
    public final Observable<KarutaExam> recentKarutaExam = recentKarutaExamSubject;

    private final PublishSubject<Unit> startedEventSubject = PublishSubject.create();
    public final Observable<Unit> startedEvent = startedEventSubject;

    private final PublishSubject<KarutaExamIdentifier> finishedEventSubject = PublishSubject.create();
    public final Observable<KarutaExamIdentifier> finishedEvent = finishedEventSubject;

    @Inject
    public ExamStore(@NonNull Dispatcher dispatcher) {
        dispatcher.on(StartExamAction.class).subscribe(action -> {
            startedEventSubject.onNext(Unit.INSTANCE);
        });
        dispatcher.on(FinishExamAction.class).subscribe(action -> {
            finishedEventSubject.onNext(action.karutaExam.identifier());
            recentKarutaExamSubject.onNext(action.karutaExam);
        });
        dispatcher.on(FetchExamAction.class).subscribe(action -> {
            karutaExamSubject.onNext(action.karutaExam);
        });
        dispatcher.on(FetchRecentExamAction.class).subscribe(action -> {
            recentKarutaExamSubject.onNext(action.karutaExam);
        });
    }
}
