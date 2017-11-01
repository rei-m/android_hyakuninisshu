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
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Enclosed.class)
public class KarutaExamsTest {

    public static class WhenNotEmpty {

        private KarutaExams karutaExams;

        private List<KarutaExam> values;

        @Before
        public void setUp() throws Exception {
            values = new ArrayList<KarutaExam>() {{
                int[] ids1 = new int[1];
                ids1[0] = 1;
                add(createMockExam(createKarutaIds(ids1)));

                int[] ids2 = new int[3];
                ids2[0] = 1;
                ids2[1] = 2;
                ids2[2] = 3;
                add(createMockExam(createKarutaIds(ids2)));

                add(createMockExam(createKarutaIds(new int[0])));
            }};

            karutaExams = new KarutaExams(values);
        }

        @Test
        public void recent() throws Exception {
            assertThat(karutaExams.recent(), is(values.get(0)));
        }

        @Test
        public void totalWrongKarutaIds() throws Exception {
            int[] ids = new int[3];
            ids[0] = 1;
            ids[1] = 2;
            ids[2] = 3;
            KarutaIds expected = createKarutaIds(ids);
            assertThat(karutaExams.totalWrongKarutaIds(), is(expected));
        }
    }

    public static class WhenEmpty {

        private KarutaExams karutaExams;

        private List<KarutaExam> values;

        @Before
        public void setUp() throws Exception {
            values = new ArrayList<>();
            karutaExams = new KarutaExams(values);
        }

        @Test
        public void recent() throws Exception {
            assertThat(karutaExams.recent(), nullValue());
        }

        @Test
        public void totalWrongKarutaIds() throws Exception {
            KarutaIds expected = createKarutaIds(new int[0]);
            assertThat(karutaExams.totalWrongKarutaIds(), is(expected));
        }
    }

    private static KarutaIds createKarutaIds(int[] ids) {
        List<KarutaIdentifier> identifiers = new ArrayList<>();
        for (int id : ids) {
            identifiers.add(new KarutaIdentifier(id));
        }
        return new KarutaIds(identifiers);
    }

    private static KarutaExam createMockExam(KarutaIds wrongKarutaIds) {
        KarutaExam karutaExam = mock(KarutaExam.class);
        KarutaExamResult karutaExamResult = mock(KarutaExamResult.class);

        when(karutaExamResult.wrongKarutaIds()).thenReturn(wrongKarutaIds);
        when(karutaExam.result()).thenReturn(karutaExamResult);

        return karutaExam;
    }
}