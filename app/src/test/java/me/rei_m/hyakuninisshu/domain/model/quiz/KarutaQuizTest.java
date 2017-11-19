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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class KarutaQuizTest {

    private static final List<KarutaIdentifier> choiceList = new ArrayList<KarutaIdentifier>() {{
        add(new KarutaIdentifier(1));
        add(new KarutaIdentifier(2));
        add(new KarutaIdentifier(3));
        add(new KarutaIdentifier(4));
    }};

    private static final KarutaIdentifier correctId = new KarutaIdentifier(1);

    public static class WhenNotStarted {

        private KarutaQuiz karutaQuiz;

        @Before
        public void setUp() throws Exception {
            karutaQuiz = new KarutaQuiz(new KarutaQuizIdentifier(), choiceList, correctId);
        }

        @Test
        public void choiceList() throws Exception {
            assertThat(karutaQuiz.choiceList(), is(choiceList));
        }

        @Test
        public void correctId() throws Exception {
            assertThat(karutaQuiz.correctId(), is(correctId));
        }

        @Test
        public void startDate() throws Exception {
            assertThat(karutaQuiz.startDate(), nullValue());
        }

        @Test
        public void result() throws Exception {
            assertThat(karutaQuiz.result(), nullValue());
        }

        @Test
        public void start() throws Exception {
            Date startDate = new Date();
            KarutaQuiz actual = karutaQuiz.start(startDate);
            assertThat(actual, is(karutaQuiz));
            assertThat(karutaQuiz.startDate(), is(startDate));
        }

        @Test(expected = IllegalStateException.class)
        public void verify() throws Exception {
            karutaQuiz.verify(ChoiceNo.FIRST, new Date());
        }
    }

    public static class WhenStarted {

        private KarutaQuiz karutaQuiz;

        private Date startDate;

        @Before
        public void setUp() throws Exception {
            startDate = new Date();
            karutaQuiz = new KarutaQuiz(new KarutaQuizIdentifier(), choiceList, correctId, startDate);
        }

        @Test
        public void startDate() throws Exception {
            assertThat(karutaQuiz.startDate(), is(startDate));
        }

        @Test
        public void result() throws Exception {
            assertThat(karutaQuiz.result(), nullValue());
        }

        @SuppressWarnings("ConstantConditions")
        @Test
        public void verifyWhenCorrect() throws Exception {
            Date answerDate = new Date();
            KarutaQuiz actual = karutaQuiz.verify(ChoiceNo.FIRST, answerDate);
            assertThat(actual, is(karutaQuiz));
            assertThat(actual.result().choiceNo(), is(ChoiceNo.FIRST));
            assertThat(actual.result().judgement(), is(new KarutaQuizJudgement(correctId, true)));
            assertThat(actual.result().answerMillSec(), is(answerDate.getTime() - startDate.getTime()));
        }

        @SuppressWarnings("ConstantConditions")
        @Test
        public void verifyWhenNotCorrect() throws Exception {
            Date answerDate = new Date();
            KarutaQuiz actual = karutaQuiz.verify(ChoiceNo.SECOND, answerDate);
            assertThat(actual, is(karutaQuiz));
            assertThat(actual.result().choiceNo(), is(ChoiceNo.SECOND));
            assertThat(actual.result().judgement(), is(new KarutaQuizJudgement(correctId, false)));
            assertThat(actual.result().answerMillSec(), is(answerDate.getTime() - startDate.getTime()));
        }
    }

    public static class WhenAnswered {

        private KarutaQuiz karutaQuiz;

        private Date startDate;

        @Before
        public void setUp() throws Exception {
            startDate = new Date();
            karutaQuiz = new KarutaQuiz(new KarutaQuizIdentifier(),
                    choiceList,
                    correctId,
                    startDate,
                    5000,
                    ChoiceNo.FIRST,
                    true);
        }

        @Test
        public void startDate() throws Exception {
            assertThat(karutaQuiz.startDate(), is(startDate));
        }

        @Test
        public void result() throws Exception {
            KarutaQuizResult expected = new KarutaQuizResult(correctId,
                    ChoiceNo.FIRST,
                    true,
                    5000
            );

            assertThat(karutaQuiz.result(), is(expected));
        }
    }
}