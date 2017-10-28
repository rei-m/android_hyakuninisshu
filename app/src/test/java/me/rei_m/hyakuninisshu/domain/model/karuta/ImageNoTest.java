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

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ImageNoTest {
    @Test
    public void createWith001() throws Exception {
        ImageNo imageNo = new ImageNo("001");
        assertThat(imageNo.value(), is("001"));
    }

    @Test
    public void createWith050() throws Exception {
        ImageNo imageNo = new ImageNo("050");
        assertThat(imageNo.value(), is("050"));
    }

    @Test
    public void createWith100() throws Exception {
        ImageNo imageNo = new ImageNo("100");
        assertThat(imageNo.value(), is("100"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWith000() throws Exception {
        new ImageNo("000");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWith101() throws Exception {
        new ImageNo("101");
    }

    @Test
    public void equals() throws Exception {
        ImageNo imageNo1 = new ImageNo("100");
        ImageNo imageNo2 = new ImageNo("100");
        assertThat(imageNo1.equals(imageNo2), is(true));
    }

    @Test
    public void equalsNot() throws Exception {
        ImageNo imageNo1 = new ImageNo("100");
        ImageNo imageNo2 = new ImageNo("001");
        assertThat(imageNo1.equals(imageNo2), is(false));
    }
}
