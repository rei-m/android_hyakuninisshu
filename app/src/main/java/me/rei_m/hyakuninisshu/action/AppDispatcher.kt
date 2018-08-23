/*
 * Copyright (c) 2018. Rei Matsushita
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

package me.rei_m.hyakuninisshu.action

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.processors.PublishProcessor
import me.rei_m.hyakuninisshu.util.Logger
import javax.inject.Singleton

@Singleton
class AppDispatcher(private val uiScheduler: Scheduler) : Dispatcher {

    private val processor = PublishProcessor.create<Action>()

    override fun dispatch(action: Action) {
        Logger.action(action)
        processor.onNext(action)
    }

    override fun <T : Action> on(clazz: Class<T>): Observable<T> = processor.onBackpressureBuffer()
        .ofType(clazz)
        .observeOn(uiScheduler)
        .toObservable()
}
