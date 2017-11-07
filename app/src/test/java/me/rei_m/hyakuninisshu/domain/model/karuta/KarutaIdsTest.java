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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class KarutaIdsTest {

    private KarutaIds karutaIds;

    @Before
    public void setUp() throws Exception {
        karutaIds = new KarutaIds(new ArrayList<KarutaIdentifier>() {
            {
                add(new KarutaIdentifier(1));
                add(new KarutaIdentifier(2));
                add(new KarutaIdentifier(3));
            }
        });
    }

    @Test
    public void asList() throws Exception {
        List<KarutaIdentifier> expected = new ArrayList<KarutaIdentifier>() {
            {
                add(new KarutaIdentifier(1));
                add(new KarutaIdentifier(2));
                add(new KarutaIdentifier(3));
            }
        };
        assertThat(karutaIds.asList(), is(expected));
    }

    @Test
    public void size() throws Exception {
        assertThat(karutaIds.size(), is(3));
    }

    @Test
    public void contains() throws Exception {
        assertThat(karutaIds.contains(new KarutaIdentifier(1)), is(true));
    }

    @Test
    public void notContains() throws Exception {
        assertThat(karutaIds.contains(new KarutaIdentifier(4)), is(false));
    }
}
