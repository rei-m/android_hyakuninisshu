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

package me.rei_m.hyakuninisshu.domain.model.karuta;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class KarutaTest {

    private Karuta karuta;
    private String creator;
    private KamiNoKu kamiNoKu;
    private ShimoNoKu shimoNoKu;
    private ImageNo imageNo;
    private String translation;

    @Before
    public void setUp() throws Exception {
        KarutaIdentifier identifier = new KarutaIdentifier(1);
        creator = "creator";
        kamiNoKu = new KamiNoKu(
                new KamiNoKuIdentifier(),
                new Phrase("しょく", "初句"),
                new Phrase("にく", "二句"),
                new Phrase("さんく", "三句")
        );
        shimoNoKu = new ShimoNoKu(
                new ShimoNoKuIdentifier(),
                new Phrase("よんく", "四句"),
                new Phrase("ごく", "五句")
        );
        imageNo = new ImageNo("001");
        translation = "歌の訳";
        karuta = new Karuta(identifier, creator, kamiNoKu, shimoNoKu, Kimariji.ONE, imageNo, translation, Color.BLUE);
    }

    @Test
    public void creator() throws Exception {
        assertThat(karuta.creator(), is(creator));
    }

    @Test
    public void kamiNoKu() throws Exception {
        assertThat(karuta.kamiNoKu(), is(kamiNoKu));
    }

    @Test
    public void shimoNoKu() throws Exception {
        assertThat(karuta.shimoNoKu(), is(shimoNoKu));
    }

    @Test
    public void kimariji() throws Exception {
        assertThat(karuta.kimariji(), is(Kimariji.ONE));
    }

    @Test
    public void imageNo() throws Exception {
        assertThat(karuta.imageNo(), is(imageNo));
    }

    @Test
    public void translation() throws Exception {
        assertThat(karuta.translation(), is(translation));
    }

    @Test
    public void color() throws Exception {
        assertThat(karuta.color(), is(Color.BLUE));
    }

    @RunWith(Theories.class)
    public static class UpdatePhrase {

    }

    @Test
    public void updatePhrase() throws Exception {
        Karuta updated = karuta.updatePhrase(
                "新初句",
                "しんしょく",
                "新二句",
                "しんにく",
                "新三句",
                "しんさんく",
                "新四句",
                "しんよんく",
                "新五句",
                "しんごく"
        );
        assertThat(updated.kamiNoKu().first().kanji(), is("新初句"));
        assertThat(updated.kamiNoKu().first().kana(), is("しんしょく"));
        assertThat(updated.kamiNoKu().second().kanji(), is("新二句"));
        assertThat(updated.kamiNoKu().second().kana(), is("しんにく"));
        assertThat(updated.kamiNoKu().third().kanji(), is("新三句"));
        assertThat(updated.kamiNoKu().third().kana(), is("しんさんく"));
        assertThat(updated.shimoNoKu().fourth().kanji(), is("新四句"));
        assertThat(updated.shimoNoKu().fourth().kana(), is("しんよんく"));
        assertThat(updated.shimoNoKu().fifth().kanji(), is("新五句"));
        assertThat(updated.shimoNoKu().fifth().kana(), is("しんごく"));
    }
}
