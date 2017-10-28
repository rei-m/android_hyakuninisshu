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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class KamiNoKuTest {

    private KamiNoKu kamiNoKu;
    private Phrase first;
    private Phrase second;
    private Phrase third;

    @Before
    public void setUp() throws Exception {
        KamiNoKuIdentifier identifier = new KamiNoKuIdentifier();
        first = new Phrase("しょく", "初句");
        second = new Phrase("にく", "二句");
        third = new Phrase("さんく", "三句");
        kamiNoKu = new KamiNoKu(identifier, first, second, third);
    }

    @Test
    public void first() throws Exception {
        assertThat(kamiNoKu.first(), is(this.first));
    }

    @Test
    public void second() throws Exception {
        assertThat(kamiNoKu.second(), is(this.second));
    }

    @Test
    public void third() throws Exception {
        assertThat(kamiNoKu.third(), is(this.third));
    }

    @Test
    public void kanji() throws Exception {
        assertThat(kamiNoKu.kanji(), is("初句　二句　三句"));
    }

    @Test
    public void kana() throws Exception {
        assertThat(kamiNoKu.kana(), is("しょく　にく　さんく"));
    }
}
