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
import me.rei_m.hyakuninisshu.action.quiz.AnswerQuizAction;
import me.rei_m.hyakuninisshu.action.quiz.FetchQuizAction;
import me.rei_m.hyakuninisshu.action.quiz.StartQuizAction;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent;
import me.rei_m.hyakuninisshu.util.Unit;

@ForActivity
public class QuizStore {

    private final PublishSubject<KarutaQuizContent> karutaQuizContentSubject = PublishSubject.create();
    public final Observable<KarutaQuizContent> karutaQuizContent = karutaQuizContentSubject;

    private final PublishSubject<Unit> errorSubject = PublishSubject.create();
    public final Observable<Unit> error = errorSubject;

    @Inject
    public QuizStore(@NonNull Dispatcher dispatcher) {
        dispatcher.on(StartQuizAction.class).subscribe(action -> {
            karutaQuizContentSubject.onNext(action.karutaQuizContent);
        });
        dispatcher.on(AnswerQuizAction.class).subscribe(action -> {
            karutaQuizContentSubject.onNext(action.karutaQuizContent);
        });
        dispatcher.on(FetchQuizAction.class).subscribe(action -> {
            karutaQuizContentSubject.onNext(action.karutaQuizContent);
        });
    }
}
