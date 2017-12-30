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

package me.rei_m.hyakuninisshu.action.application;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.action.Action;
import me.rei_m.hyakuninisshu.action.Dispatcher;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;

public class ApplicationActionDispatcher implements Action {

    private final Dispatcher dispatcher;
    private final KarutaRepository karutaRepository;

    @Inject
    public ApplicationActionDispatcher(@NonNull Dispatcher dispatcher,
                                       @NonNull KarutaRepository karutaRepository) {
        this.dispatcher = dispatcher;
        this.karutaRepository = karutaRepository;
    }

    /**
     * 百人一首の情報を準備してアプリの利用を開始する.
     */
    public void start() {
        karutaRepository.initialize()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> dispatcher.dispatch(new StartApplicationAction()));
    }
}
