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
import static org.junit.Assert.*;

public class ShimoNoKuTest {

    private ShimoNoKu shimoNoKu;
    private Phrase fourth;
    private Phrase fifth;

    @Before
    public void setUp() throws Exception {
        ShimoNoKuIdentifier identifier = new ShimoNoKuIdentifier(1);
        fourth = new Phrase("よんく", "四句");
        fifth = new Phrase("ごく", "五句");
        shimoNoKu = new ShimoNoKu(identifier, fourth, fifth);
    }

    @Test
    public void fourth() throws Exception {
        assertThat(shimoNoKu.fourth(), is(this.fourth));
    }

    @Test
    public void fifth() throws Exception {
        assertThat(shimoNoKu.fifth(), is(this.fifth));
    }

    @Test
    public void kanji() throws Exception {
        assertThat(shimoNoKu.kanji(), is("四句　五句"));
    }

    @Test
    public void kana() throws Exception {
        assertThat(shimoNoKu.kana(), is("よんく　ごく"));
    }
}
