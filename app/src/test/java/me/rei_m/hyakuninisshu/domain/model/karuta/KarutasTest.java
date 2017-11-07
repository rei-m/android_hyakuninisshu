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
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

// 内部的にSparseIntArrayを使っているためRobolectricを使う.
@RunWith(RobolectricTestRunner.class)
public class KarutasTest {

    private Karutas karutas;

    private List<Karuta> karutasValue;

    @Before
    public void setUp() throws Exception {
        karutasValue = new ArrayList<Karuta>() {
            {
                add(createKaruta(1, Color.BLUE));
                add(createKaruta(2, Color.BLUE));
                add(createKaruta(3, Color.PINK));
                add(createKaruta(4, Color.PINK));
            }
        };
        karutas = new Karutas(karutasValue);
    }

    @Test
    public void asList() throws Exception {
        assertThat(karutas.asList(), is(karutasValue));
    }

    @Test
    public void asListWithColor() throws Exception {
        assertThat(karutas.asList(Color.BLUE), is(karutasValue.subList(0, 2)));
    }

    @Test
    public void createQuizSet() throws Exception {
        List<KarutaIdentifier> karutaIdentifierList = new ArrayList<KarutaIdentifier>() {
            {
                add(new KarutaIdentifier(1));
            }
        };

        KarutaIds karutaIds = new KarutaIds(karutaIdentifierList);
        assertThat(karutas.createQuizSet(karutaIds).asList().size(), is(1));
    }

    private Karuta createKaruta(int id, Color color) {
        KarutaIdentifier identifier = new KarutaIdentifier(id);
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
        return new Karuta(identifier, creator, kamiNoKu, shimoNoKu, Kimariji.ONE, imageNo, translation, color);
    }
}