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
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

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
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas;
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji;
import me.rei_m.hyakuninisshu.domain.model.karuta.Phrase;
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKu;
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKuIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExams;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzes;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzesResultSummary;
import me.rei_m.hyakuninisshu.rule.TestSchedulerRule;
import me.rei_m.hyakuninisshu.util.Unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class KarutaExamModelTest {

    @Rule
    public TestSchedulerRule rule = new TestSchedulerRule();

    private KarutaExamModel model;

    private KarutaRepository karutaRepository;
    private KarutaQuizRepository karutaQuizRepository;
    private KarutaExamRepository karutaExamRepository;

    @Before
    public void setUp() throws Exception {
        karutaRepository = mock(KarutaRepository.class);
        karutaQuizRepository = mock(KarutaQuizRepository.class);
        karutaExamRepository = mock(KarutaExamRepository.class);

        model = new KarutaExamModel(karutaRepository, karutaQuizRepository, karutaExamRepository);
    }

    @Test
    public void fetchKarutaExam() throws Exception {

        KarutaExamIdentifier identifier = new KarutaExamIdentifier(1);
        KarutaExam karutaExam = createKarutaExam(identifier);
        when(karutaExamRepository.findBy(identifier)).thenReturn(Single.just(karutaExam));

        TestObserver<KarutaExam> observer = TestObserver.create();
        model.karutaExam.subscribe(observer);
        model.fetchKarutaExam(identifier);
        observer.assertValueCount(1);
        observer.assertValue(karutaExam);
    }

    @Test
    public void fetchRecentKarutaExam() throws Exception {
        KarutaExam karutaExam = createKarutaExam(new KarutaExamIdentifier(1));
        List<KarutaExam> karutaExamList = new ArrayList<>(Arrays.asList(
                karutaExam,
                createKarutaExam(new KarutaExamIdentifier(2)),
                createKarutaExam(new KarutaExamIdentifier(3)),
                createKarutaExam(new KarutaExamIdentifier(4))
        ));
        KarutaExams karutaExams = new KarutaExams(karutaExamList);
        when(karutaExamRepository.list()).thenReturn(Single.just(karutaExams));

        TestObserver<KarutaExam> observer = TestObserver.create();
        model.recentKarutaExam.subscribe(observer);
        model.fetchRecentKarutaExam();
        observer.assertValueCount(1);
        observer.assertValue(karutaExam);
    }

    @Test
    public void fetchRecentKarutaExamWhenEmpty() throws Exception {
        KarutaExams karutaExams = new KarutaExams(new ArrayList<>());
        when(karutaExamRepository.list()).thenReturn(Single.just(karutaExams));

        TestObserver<KarutaExam> observer = TestObserver.create();
        model.recentKarutaExam.subscribe(observer);
        model.fetchRecentKarutaExam();
        observer.assertValueCount(0);
    }

    @Test
    public void start() throws Exception {
        List<Karuta> karutaList = new ArrayList<Karuta>() {{
            for (int i = 1; i <= 10; i++) {
                add(createKaruta(new KarutaIdentifier(i)));
            }
        }};
        Karutas karutas = new Karutas(karutaList);
        when(karutaRepository.list()).thenReturn(Single.just(karutas));

        List<KarutaIdentifier> karutaIdentifierList = new ArrayList<KarutaIdentifier>() {{
            for (int i = 1; i <= 10; i++) {
                add(new KarutaIdentifier(i));
            }
        }};
        KarutaIds karutaIds = new KarutaIds(karutaIdentifierList);
        when(karutaRepository.findIds()).thenReturn(Single.just(karutaIds));

        when(karutaQuizRepository.initialize(any())).thenReturn(Completable.complete());

        TestObserver<Unit> observer = TestObserver.create();
        model.startedEvent.subscribe(observer);
        model.start();
        observer.assertValueCount(1);
        observer.assertValue(Unit.INSTANCE);
    }

    @Test
    public void finish() throws Exception {

        List<KarutaIdentifier> choiceList1 = new ArrayList<>(Arrays.asList(
                new KarutaIdentifier(1),
                new KarutaIdentifier(2),
                new KarutaIdentifier(3),
                new KarutaIdentifier(4)
        ));
        KarutaQuiz karutaQuiz1 = new KarutaQuiz(
                new KarutaQuizIdentifier(),
                choiceList1,
                choiceList1.get(0),
                new Date(),
                10,
                ChoiceNo.FIRST,
                true);
        List<KarutaIdentifier> choiceList2 = new ArrayList<>(Arrays.asList(
                new KarutaIdentifier(5),
                new KarutaIdentifier(6),
                new KarutaIdentifier(7),
                new KarutaIdentifier(8)
        ));
        KarutaQuiz karutaQuiz2 = new KarutaQuiz(
                new KarutaQuizIdentifier(),
                choiceList2,
                choiceList2.get(0),
                new Date(),
                10,
                ChoiceNo.FIRST,
                true);
        KarutaQuizzes karutaQuizzes = new KarutaQuizzes(new ArrayList<>(Arrays.asList(karutaQuiz1, karutaQuiz2)));
        KarutaExamIdentifier karutaExamIdentifier = new KarutaExamIdentifier(1);
        KarutaExam karutaExam = createKarutaExam(karutaExamIdentifier);

        when(karutaQuizRepository.list()).thenReturn(Single.just(karutaQuizzes));
        when(karutaExamRepository.storeResult(any(), any())).thenReturn(Single.just(karutaExamIdentifier));
        when(karutaExamRepository.adjustHistory(anyInt())).thenReturn(Completable.complete());
        when(karutaExamRepository.findBy(karutaExamIdentifier)).thenReturn(Single.just(karutaExam));

        TestObserver<KarutaExamIdentifier> finishedEventObserver = TestObserver.create();
        model.finishedEvent.subscribe(finishedEventObserver);
        TestObserver<KarutaExam> recentKarutaExamObserver = TestObserver.create();
        model.recentKarutaExam.subscribe(recentKarutaExamObserver);

        model.finish();
        finishedEventObserver.assertValueCount(1);
        finishedEventObserver.assertValue(karutaExamIdentifier);
        recentKarutaExamObserver.assertValueCount(1);
        recentKarutaExamObserver.assertValue(karutaExam);
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

    private static KarutaExam createKarutaExam(KarutaExamIdentifier karutaExamIdentifier) {
        KarutaQuizzesResultSummary resultSummary = new KarutaQuizzesResultSummary(10, 9, 5.0f);
        List<KarutaIdentifier> karutaIdentifierList = new ArrayList<KarutaIdentifier>() {{
            add(new KarutaIdentifier(1));
        }};
        KarutaIds karutaIds = new KarutaIds(karutaIdentifierList);
        KarutaExamResult karutaExamResult = new KarutaExamResult(resultSummary, karutaIds);
        return new KarutaExam(karutaExamIdentifier, karutaExamResult);
    }
}
