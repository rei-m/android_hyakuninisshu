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

package me.rei_m.hyakuninisshu.domain.model.quiz;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.model.karuta.Color;
import me.rei_m.hyakuninisshu.domain.model.karuta.ImageNo;
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKu;
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKuIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaStyle;
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji;
import me.rei_m.hyakuninisshu.domain.model.karuta.Phrase;
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKu;
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKuIdentifier;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class KarutaQuizContentTest {

    private KarutaQuizContent karutaQuizContent;

    private KarutaQuiz karutaQuiz;

    @Before
    public void setUp() throws Exception {
        karutaQuiz = mock(KarutaQuiz.class);
        Karuta correctKaruta = createKaruta(1);
        List<Karuta> choices = new ArrayList<Karuta>() {
            {
                add(createKaruta(1));
                add(createKaruta(2));
                add(createKaruta(3));
                add(createKaruta(4));
            }
        };
        KarutaQuizCounter counter = new KarutaQuizCounter(100, 10);
        karutaQuizContent = new KarutaQuizContent(karutaQuiz, correctKaruta, choices, counter, true);
    }

    @Test
    public void quiz() throws Exception {
        assertThat(karutaQuizContent.quiz(), is(karutaQuiz));
    }

    @Test
    public void yomiFudaByKana() throws Exception {
        YomiFuda expected = new YomiFuda("しょく_1", "にく_1", "さんく_1");
        assertThat(karutaQuizContent.yomiFuda(KarutaStyle.KANA), is(expected));
    }

    @Test
    public void yomiFudaByKanji() throws Exception {
        YomiFuda expected = new YomiFuda("初句_1", "二句_1", "三句_1");
        assertThat(karutaQuizContent.yomiFuda(KarutaStyle.KANJI), is(expected));
    }

    @Test
    public void toriFudasByKana() throws Exception {
        List<ToriFuda> expected = new ArrayList<ToriFuda>() {
            {
                add(new ToriFuda("よんく_1", "ごく_1"));
                add(new ToriFuda("よんく_2", "ごく_2"));
                add(new ToriFuda("よんく_3", "ごく_3"));
                add(new ToriFuda("よんく_4", "ごく_4"));
            }
        };
        assertThat(karutaQuizContent.toriFudas(KarutaStyle.KANA), is(expected));
    }

    @Test
    public void toriFudasByKanji() throws Exception {
        List<ToriFuda> expected = new ArrayList<ToriFuda>() {
            {
                add(new ToriFuda("四句_1", "五句_1"));
                add(new ToriFuda("四句_2", "五句_2"));
                add(new ToriFuda("四句_3", "五句_3"));
                add(new ToriFuda("四句_4", "五句_4"));
            }
        };
        assertThat(karutaQuizContent.toriFudas(KarutaStyle.KANJI), is(expected));
    }

    @Test
    public void currentPosition() throws Exception {
        assertThat(karutaQuizContent.currentPosition(), is("11 / 100"));
    }

    @Test
    public void existNext() throws Exception {
        assertThat(karutaQuizContent.existNext(), is(true));
    }

    private static Karuta createKaruta(int id) {
        KarutaIdentifier identifier = new KarutaIdentifier(id);
        String creator = "creator";
        KamiNoKu kamiNoKu = new KamiNoKu(
                new KamiNoKuIdentifier(identifier.value()),
                new Phrase("しょく_" + id, "初句_" + id),
                new Phrase("にく_" + id, "二句_" + id),
                new Phrase("さんく_" + id, "三句_" + id)
        );
        ShimoNoKu shimoNoKu = new ShimoNoKu(
                new ShimoNoKuIdentifier(identifier.value()),
                new Phrase("よんく_" + id, "四句_" + id),
                new Phrase("ごく_" + id, "五句_" + id)
        );
        ImageNo imageNo = new ImageNo("001");
        String translation = "歌の訳";
        return new Karuta(identifier, creator, kamiNoKu, shimoNoKu, Kimariji.ONE, imageNo, translation, Color.BLUE);
    }
}
