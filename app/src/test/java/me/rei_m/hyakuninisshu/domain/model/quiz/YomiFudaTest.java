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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class YomiFudaTest {

    private YomiFuda yomiFuda;

    @Before
    public void setUp() throws Exception {
        yomiFuda = new YomiFuda("しょく", "にく", "さんく");
    }

    @Test
    public void firstPhrase() throws Exception {
        assertThat(yomiFuda.firstPhrase(), is("しょく"));
    }

    @Test
    public void secondPhrase() throws Exception {
        assertThat(yomiFuda.secondPhrase(), is("にく"));
    }

    @Test
    public void thirdPhrase() throws Exception {
        assertThat(yomiFuda.thirdPhrase(), is("さんく"));
    }
}