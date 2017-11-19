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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import me.rei_m.hyakuninisshu.domain.model.karuta.Color;
import me.rei_m.hyakuninisshu.domain.model.karuta.ImageNo;
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKu;
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKuIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji;
import me.rei_m.hyakuninisshu.domain.model.karuta.Phrase;
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKu;
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKuIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizCounter;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.rule.TestSchedulerRule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KarutaQuizModelTest {

    @Rule
    public TestSchedulerRule rule = new TestSchedulerRule();

    private KarutaQuizModel model;

    private KarutaQuizRepository karutaQuizRepository;

    private List<KarutaIdentifier> choiceList;
    private List<Karuta> karutaList;
    private KarutaQuizCounter counter;

    @Before
    public void setUp() throws Exception {
        KarutaRepository karutaRepository = mock(KarutaRepository.class);

        choiceList = new ArrayList<>(Arrays.asList(
                new KarutaIdentifier(1),
                new KarutaIdentifier(2),
                new KarutaIdentifier(3),
                new KarutaIdentifier(4)
        ));
        karutaList = new ArrayList<>(Arrays.asList(
                createKaruta(choiceList.get(0)),
                createKaruta(choiceList.get(1)),
                createKaruta(choiceList.get(2)),
                createKaruta(choiceList.get(3))
        ));

        when(karutaRepository.findBy(choiceList.get(0))).thenReturn(Single.just(karutaList.get(0)));
        when(karutaRepository.findBy(choiceList.get(1))).thenReturn(Single.just(karutaList.get(1)));
        when(karutaRepository.findBy(choiceList.get(2))).thenReturn(Single.just(karutaList.get(2)));
        when(karutaRepository.findBy(choiceList.get(3))).thenReturn(Single.just(karutaList.get(3)));

        karutaQuizRepository = mock(KarutaQuizRepository.class);

        counter = new KarutaQuizCounter(10, 1);

        when(karutaQuizRepository.countQuizByAnswered()).thenReturn(Single.just(counter));
        when(karutaQuizRepository.existNextQuiz()).thenReturn(Single.just(true));

        model = new KarutaQuizModel(karutaRepository, karutaQuizRepository);
    }

    @Test
    public void start() throws Exception {
        KarutaQuiz karutaQuiz = new KarutaQuiz(
                new KarutaQuizIdentifier(),
                choiceList,
                choiceList.get(0));

        when(karutaQuizRepository.first()).thenReturn(Single.just(karutaQuiz));
        when(karutaQuizRepository.store(karutaQuiz)).thenReturn(Completable.complete());

        TestObserver<KarutaQuizContent> observer = TestObserver.create();
        model.karutaQuizContent.subscribe(observer);
        model.start();
        observer.assertValueCount(1);
        observer.assertValue(new KarutaQuizContent(karutaQuiz, karutaList.get(0), karutaList, counter, true));
    }

    @Test
    public void answer() throws Exception {
        KarutaQuiz karutaQuiz = new KarutaQuiz(
                new KarutaQuizIdentifier(),
                choiceList,
                choiceList.get(0),
                new Date());

        when(karutaQuizRepository.findBy(karutaQuiz.identifier())).thenReturn(Single.just(karutaQuiz));
        when(karutaQuizRepository.store(karutaQuiz)).thenReturn(Completable.complete());

        TestObserver<KarutaQuizContent> observer = TestObserver.create();
        model.karutaQuizContent.subscribe(observer);
        model.answer(karutaQuiz.identifier(), ChoiceNo.FIRST);
        observer.assertValueCount(1);
        observer.assertValue(new KarutaQuizContent(karutaQuiz, karutaList.get(0), karutaList, counter, true));
    }

    private static Karuta createKaruta(KarutaIdentifier identifier) {
        String creator = "creator";
        KamiNoKu kamiNoKu = new KamiNoKu(
                new KamiNoKuIdentifier(identifier.value()),
                new Phrase("しょく", "初句"),
                new Phrase("にく", "二句"),
                new Phrase("さんく", "三句")
        );
        ShimoNoKu shimoNoKu = new ShimoNoKu(
                new ShimoNoKuIdentifier(identifier.value()),
                new Phrase("よんく", "四句"),
                new Phrase("ごく", "五句")
        );
        ImageNo imageNo = new ImageNo("001");
        String translation = "歌の訳";
        return new Karuta(identifier, creator, kamiNoKu, shimoNoKu, Kimariji.ONE, imageNo, translation, Color.BLUE);
    }
}