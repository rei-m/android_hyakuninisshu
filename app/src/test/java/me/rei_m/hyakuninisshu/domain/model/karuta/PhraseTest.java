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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

public class PhraseTest {

    private Phrase phrase;

    @Before
    public void setUp() throws Exception {
        phrase = new Phrase("かな", "漢字");
    }

    @Test
    public void kana() throws Exception {
        assertThat(phrase.kana(), is("かな"));
    }

    @Test
    public void kanji() throws Exception {
        assertThat(phrase.kanji(), is("漢字"));
    }

    @Test
    public void equals() throws Exception {
        Phrase that = new Phrase("かな", "漢字");
        assertEquals(phrase, that);
    }

    @Test
    public void notEquals() throws Exception {
        Phrase that = new Phrase("かなかな", "漢字");
        assertNotEquals(phrase, that);
    }
}