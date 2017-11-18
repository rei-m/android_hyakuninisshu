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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.reactivex.Completable;
import io.reactivex.observers.TestObserver;
import me.rei_m.hyakuninisshu.rule.TestSchedulerRule;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.util.Unit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApplicationModelTest {

    @Rule
    public TestSchedulerRule rule = new TestSchedulerRule();

    private ApplicationModel model;

    @Before
    public void setUp() throws Exception {
        KarutaRepository karutaRepository = mock(KarutaRepository.class);
        when(karutaRepository.initialize()).thenReturn(Completable.complete());
        model = new ApplicationModel(karutaRepository);
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
