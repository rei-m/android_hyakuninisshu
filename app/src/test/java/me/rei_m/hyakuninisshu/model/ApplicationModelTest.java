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

package me.rei_m.hyakuninisshu.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.util.Unit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApplicationModelTest {

    private ApplicationModel model;

    @Before
    public void setUp() throws Exception {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());

        KarutaRepository karutaRepository = mock(KarutaRepository.class);
        when(karutaRepository.initialize()).thenReturn(Completable.complete());
        model = new ApplicationModel(karutaRepository);
    }

    @After
    public void tearDown() throws Exception {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @Test
    public void start() throws Exception {
        TestObserver<Unit> observer = TestObserver.create();
        model.readyEvent.subscribe(observer);
        model.start();
        observer.assertValueCount(1);
        observer.assertValue(Unit.INSTANCE);
    }
}
