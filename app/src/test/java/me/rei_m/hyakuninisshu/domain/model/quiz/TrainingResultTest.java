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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class TrainingResultTest {

    public static class Common {

        private TrainingResult trainingResult;

        @Before
        public void setUp() throws Exception {
            trainingResult = new TrainingResult(new KarutaQuizzesResultSummary(10, 5, 5f));
        }

        @Test
        public void quizCount() throws Exception {
            assertThat(trainingResult.quizCount(), is(10));
        }

        @Test
        public void correctCount() throws Exception {
            assertThat(trainingResult.correctCount(), is(5));
        }

        @Test
        public void averageAnswerTime() throws Exception {
            assertThat(trainingResult.averageAnswerTime(), is(5f));
        }
    }

    public static class WhenCanRestart {

        private TrainingResult trainingResult;

        @Before
        public void setUp() throws Exception {
            trainingResult = new TrainingResult(new KarutaQuizzesResultSummary(10, 5, 5f));
        }

        @Test
        public void canRestartTraining() throws Exception {
            assertThat(trainingResult.canRestartTraining(), is(true));
        }
    }

    public static class WhenCanNotRestart {

        private TrainingResult trainingResult;

        @Before
        public void setUp() throws Exception {
            trainingResult = new TrainingResult(new KarutaQuizzesResultSummary(10, 10, 5f));
        }

        @Test
        public void canRestartTraining() throws Exception {
            assertThat(trainingResult.canRestartTraining(), is(false));
        }
    }
}