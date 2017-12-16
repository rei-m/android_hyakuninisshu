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
//
//import java.util.ArrayList;
//import java.util.Arrays;
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
//import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
//import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas;
//import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji;
//import me.rei_m.hyakuninisshu.domain.model.karuta.Phrase;
//import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKu;
//import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKuIdentifier;
//import me.rei_m.hyakuninisshu.rule.TestSchedulerRule;
//import me.rei_m.hyakuninisshu.util.Unit;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class KarutaModelTest {
//
//    @Rule
//    public TestSchedulerRule rule = new TestSchedulerRule();
//
//    private KarutaModel model;
//
//    private KarutaRepository karutaRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        karutaRepository = mock(KarutaRepository.class);
//        model = new KarutaModel(karutaRepository);
//    }
//
//    @Test
//    public void fetchKaruta() throws Exception {
//        KarutaIdentifier identifier = new KarutaIdentifier(1);
//        Karuta karuta = createKaruta(identifier);
//        when(karutaRepository.findBy(identifier)).thenReturn(Single.just(karuta));
//        TestObserver<Karuta> observer = TestObserver.create();
//
//        model.karuta.subscribe(observer);
//        model.fetchKaruta(identifier);
//        observer.assertValueCount(1);
//        observer.assertValue(karuta);
//    }
//
//    @Test
//    public void fetchKarutaWhenReceiveException() throws Exception {
//        KarutaIdentifier identifier = new KarutaIdentifier(1);
//        when(karutaRepository.findBy(identifier)).thenReturn(Single.error(new Exception()));
//        TestObserver<Unit> observer = TestObserver.create();
//
//        model.errorEvent.subscribe(observer);
//        model.fetchKaruta(identifier);
//        observer.assertValueCount(1);
//        observer.assertValue(Unit.INSTANCE);
//    }
//
//    @Test
//    public void editKaruta() throws Exception {
//        KarutaIdentifier identifier = new KarutaIdentifier(1);
//        Karuta karuta = createKaruta(identifier);
//        when(karutaRepository.findBy(identifier)).thenReturn(Single.just(karuta));
//        when(karutaRepository.store(karuta)).thenReturn(Completable.complete());
//        TestObserver<Karuta> karutaObserver = TestObserver.create();
//        TestObserver<Unit> editedEventObserver = TestObserver.create();
//
//        model.karuta.subscribe(karutaObserver);
//        model.editedEvent.subscribe(editedEventObserver);
//
//        model.editKaruta(identifier,
//                "一",
//                "いち",
//                "二",
//                "に",
//                "三",
//                "さん",
//                "四",
//                "よん",
//                "五",
//                "ご"
//        );
//        karutaObserver.assertValueCount(1);
//        karutaObserver.assertValue(karuta);
//        editedEventObserver.assertValueCount(1);
//        editedEventObserver.assertValue(Unit.INSTANCE);
//    }
//
//    @Test
//    public void editKarutaWhenReceiveException() throws Exception {
//        KarutaIdentifier identifier = new KarutaIdentifier(1);
//        when(karutaRepository.findBy(identifier)).thenReturn(Single.error(new Exception()));
//        TestObserver<Unit> observer = TestObserver.create();
//
//        model.errorEvent.subscribe(observer);
//        model.editKaruta(identifier,
//                "一",
//                "いち",
//                "二",
//                "に",
//                "三",
//                "さん",
//                "四",
//                "よん",
//                "五",
//                "ご"
//        );
//        observer.assertValueCount(1);
//        observer.assertValue(Unit.INSTANCE);
//    }
//
//    @Test
//    public void fetchKarutas() throws Exception {
//        List<Karuta> karutaList = new ArrayList<>(Arrays.asList(
//                createKaruta(new KarutaIdentifier(1)),
//                createKaruta(new KarutaIdentifier(2)),
//                createKaruta(new KarutaIdentifier(3))
//        ));
//        when(karutaRepository.list()).thenReturn(Single.just(new Karutas(karutaList)));
//
//        TestObserver<Karuta> observer = TestObserver.create();
//
//        model.karuta.subscribe(observer);
//        model.fetchKarutas(Color.BLUE);
//        observer.assertValueAt(0, karutaList.get(0));
//        observer.assertValueAt(1, karutaList.get(1));
//        observer.assertValueAt(2, karutaList.get(2));
//    }
//
//    @Test
//    public void fetchKarutasWhenReceiveException() throws Exception {
//        when(karutaRepository.list()).thenReturn(Single.error(new Exception()));
//
//        TestObserver<Unit> observer = TestObserver.create();
//
//        model.errorEvent.subscribe(observer);
//        model.fetchKarutas(Color.BLUE);
//        observer.assertValueCount(1);
//        observer.assertValue(Unit.INSTANCE);
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