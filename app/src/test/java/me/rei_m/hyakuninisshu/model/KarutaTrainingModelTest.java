///*
// * Copyright (c) 2017. Rei Matsushita
// *
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
// * compliance with the License. You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software distributed under the License is
// * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
// * the License for the specific language governing permissions and limitations under the License.
// */
//
//package me.rei_m.hyakuninisshu.model;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.RobolectricTestRunner;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//import io.reactivex.Completable;
//import io.reactivex.Single;
//import io.reactivex.observers.TestObserver;
//import me.rei_m.hyakuninisshu.domain.model.karuta.Color;
//import me.rei_m.hyakuninisshu.domain.model.karuta.ImageNo;
//import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKu;
//import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKuIdentifier;
//import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
//import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
//import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;
//import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
//import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas;
//import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji;
//import me.rei_m.hyakuninisshu.domain.model.karuta.Phrase;
//import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKu;
//import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKuIdentifier;
//import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo;
//import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam;
//import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
//import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository;
//import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult;
//import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExams;
//import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz;
//import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
//import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
//import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzes;
//import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzesResultSummary;
//import me.rei_m.hyakuninisshu.domain.model.quiz.TrainingResult;
//import me.rei_m.hyakuninisshu.rule.TestSchedulerRule;
//import me.rei_m.hyakuninisshu.util.Unit;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@RunWith(RobolectricTestRunner.class)
//public class KarutaTrainingModelTest {
//
//    @Rule
//    public TestSchedulerRule rule = new TestSchedulerRule();
//
//    private KarutaTrainingModel model;
//
//    private KarutaRepository karutaRepository;
//    private KarutaQuizRepository karutaQuizRepository;
//    private KarutaExamRepository karutaExamRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        karutaRepository = mock(KarutaRepository.class);
//        karutaQuizRepository = mock(KarutaQuizRepository.class);
//        karutaExamRepository = mock(KarutaExamRepository.class);
//
//        when(karutaQuizRepository.initialize(any())).thenReturn(Completable.complete());
//
//        model = new KarutaTrainingModel(karutaRepository, karutaQuizRepository, karutaExamRepository);
//    }
//
//    @Test
//    public void start() throws Exception {
//        List<KarutaIdentifier> karutaIdentifierList = new ArrayList<>(Arrays.asList(
//                new KarutaIdentifier(1),
//                new KarutaIdentifier(2),
//                new KarutaIdentifier(3)
//        ));
//
//        when(karutaRepository.list()).thenReturn(Single.just(createKarutas()));
//        when(karutaRepository.findIds(new KarutaIdentifier(1), new KarutaIdentifier(3), null, null))
//                .thenReturn(Single.just(new KarutaIds(karutaIdentifierList)));
//
//        TestObserver<Unit> observer = TestObserver.create();
//        model.startedEvent.subscribe(observer);
//        model.start(new KarutaIdentifier(1), new KarutaIdentifier(3), null, null);
//        observer.assertValueCount(1);
//        observer.assertValue(Unit.INSTANCE);
//    }
//
//    @Test
//    public void startForExam() throws Exception {
//        KarutaQuizzesResultSummary resultSummary = new KarutaQuizzesResultSummary(10, 7, 5.0f);
//        KarutaIds karutaIds = new KarutaIds(new ArrayList<>(Arrays.asList(
//                new KarutaIdentifier(1),
//                new KarutaIdentifier(2),
//                new KarutaIdentifier(3)
//        )));
//        KarutaExamResult karutaExamResult = new KarutaExamResult(resultSummary, karutaIds);
//        KarutaExam karutaExam = new KarutaExam(new KarutaExamIdentifier(1), karutaExamResult);
//
//        when(karutaRepository.list()).thenReturn(Single.just(createKarutas()));
//        when(karutaExamRepository.list()).thenReturn(Single.just(new KarutaExams(new ArrayList<>(Collections.singletonList(karutaExam)))));
//
//        TestObserver<Unit> observer = TestObserver.create();
//        model.startedEvent.subscribe(observer);
//        model.startForExam();
//        observer.assertValueCount(1);
//        observer.assertValue(Unit.INSTANCE);
//    }
//
//    @Test
//    public void restartForPractice() throws Exception {
//        List<KarutaIdentifier> choiceList1 = new ArrayList<>(Arrays.asList(
//                new KarutaIdentifier(1),
//                new KarutaIdentifier(2),
//                new KarutaIdentifier(3),
//                new KarutaIdentifier(4)
//        ));
//        KarutaQuiz karutaQuiz1 = new KarutaQuiz(
//                new KarutaQuizIdentifier(),
//                choiceList1,
//                choiceList1.get(0),
//                new Date(),
//                10,
//                ChoiceNo.SECOND,
//                false);
//        List<KarutaIdentifier> choiceList2 = new ArrayList<>(Arrays.asList(
//                new KarutaIdentifier(5),
//                new KarutaIdentifier(6),
//                new KarutaIdentifier(7),
//                new KarutaIdentifier(8)
//        ));
//        KarutaQuiz karutaQuiz2 = new KarutaQuiz(
//                new KarutaQuizIdentifier(),
//                choiceList2,
//                choiceList2.get(0),
//                new Date(),
//                10,
//                ChoiceNo.FIRST,
//                true);
//        KarutaQuizzes karutaQuizzes = new KarutaQuizzes(new ArrayList<>(Arrays.asList(karutaQuiz1, karutaQuiz2)));
//
//        when(karutaRepository.list()).thenReturn(Single.just(createKarutas()));
//        when(karutaQuizRepository.list()).thenReturn(Single.just(karutaQuizzes));
//
//        TestObserver<Unit> observer = TestObserver.create();
//        model.startedEvent.subscribe(observer);
//        model.restartForPractice();
//        observer.assertValueCount(1);
//        observer.assertValue(Unit.INSTANCE);
//    }
//
//    @Test
//    public void aggregateResults() throws Exception {
//        List<KarutaIdentifier> choiceList1 = new ArrayList<>(Arrays.asList(
//                new KarutaIdentifier(1),
//                new KarutaIdentifier(2),
//                new KarutaIdentifier(3),
//                new KarutaIdentifier(4)
//        ));
//        KarutaQuiz karutaQuiz1 = new KarutaQuiz(
//                new KarutaQuizIdentifier(),
//                choiceList1,
//                choiceList1.get(0),
//                new Date(),
//                10000,
//                ChoiceNo.SECOND,
//                false);
//        List<KarutaIdentifier> choiceList2 = new ArrayList<>(Arrays.asList(
//                new KarutaIdentifier(5),
//                new KarutaIdentifier(6),
//                new KarutaIdentifier(7),
//                new KarutaIdentifier(8)
//        ));
//        KarutaQuiz karutaQuiz2 = new KarutaQuiz(
//                new KarutaQuizIdentifier(),
//                choiceList2,
//                choiceList2.get(0),
//                new Date(),
//                5000,
//                ChoiceNo.FIRST,
//                true);
//        KarutaQuizzes karutaQuizzes = new KarutaQuizzes(new ArrayList<>(Arrays.asList(karutaQuiz1, karutaQuiz2)));
//
//        when(karutaQuizRepository.list()).thenReturn(Single.just(karutaQuizzes));
//
//        TestObserver<TrainingResult> observer = TestObserver.create();
//        model.result.subscribe(observer);
//        model.aggregateResults();
//        observer.assertValueCount(1);
//        observer.assertValue(new TrainingResult(new KarutaQuizzesResultSummary(2, 1, 7.5f)));
//    }
//
//    private static Karutas createKarutas() {
//        List<Karuta> karutaList = new ArrayList<>(Arrays.asList(
//                createKaruta(new KarutaIdentifier(1)),
//                createKaruta(new KarutaIdentifier(2)),
//                createKaruta(new KarutaIdentifier(3)),
//                createKaruta(new KarutaIdentifier(4)),
//                createKaruta(new KarutaIdentifier(5))
//        ));
//        return new Karutas(karutaList);
//    }
//
//    private static Karuta createKaruta(KarutaIdentifier identifier) {
//        String creator = "creator";
//        KamiNoKu kamiNoKu = new KamiNoKu(
//                new KamiNoKuIdentifier(identifier.value()),
//                new Phrase("しょく", "初句"),
//                new Phrase("にく", "二句"),
//                new Phrase("さんく", "三句")
//        );
//        ShimoNoKu shimoNoKu = new ShimoNoKu(
//                new ShimoNoKuIdentifier(identifier.value()),
//                new Phrase("よんく", "四句"),
//                new Phrase("ごく", "五句")
//        );
//        ImageNo imageNo = new ImageNo("001");
//        String translation = "歌の訳";
//        return new Karuta(identifier, creator, kamiNoKu, shimoNoKu, Kimariji.ONE, imageNo, translation, Color.BLUE);
//    }
//}
