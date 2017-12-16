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

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.ValueObject;
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKu;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaStyle;
import me.rei_m.hyakuninisshu.domain.model.karuta.Phrase;
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKu;

/**
 * 百人一首の問題内容.
 */
public class KarutaQuizContent implements ValueObject {

    private final KarutaQuiz karutaQuiz;

    private final Karuta correct;

    private final List<Karuta> choices;

    private final KarutaQuizCounter position;

    private final boolean existNext;

    public KarutaQuizContent(@NonNull KarutaQuiz karutaQuiz,
                             @NonNull Karuta correct,
                             @NonNull List<Karuta> choices,
                             @NonNull KarutaQuizCounter position,
                             boolean existNext) {
        this.karutaQuiz = karutaQuiz;
        this.correct = correct;
        this.choices = choices;
        this.position = position;
        this.existNext = existNext;
    }

    /**
     * @return 問題
     */
    public KarutaQuiz quiz() {
        return karutaQuiz;
    }

    /**
     * @param karutaStyle 表示スタイル
     * @return 読み札
     */
    public YomiFuda yomiFuda(@NonNull KarutaStyle karutaStyle) {
        KamiNoKu phrase = correct.kamiNoKu();
        return (karutaStyle == KarutaStyle.KANJI) ?
                new YomiFuda(phrase.first().kanji(), phrase.second().kanji(), phrase.third().kanji()) :
                new YomiFuda(phrase.first().kana(), phrase.second().kana(), phrase.third().kana());
    }

    /**
     * @param karutaStyle 表示スタイル
     * @return 取り札
     */
    public List<ToriFuda> toriFudas(@NonNull KarutaStyle karutaStyle) {
        Phrase[] fourthPhrases = new Phrase[choices.size()];
        Phrase[] fifthPhrases = new Phrase[choices.size()];

        for (int i = 0; i < choices.size(); i++) {
            ShimoNoKu shimoNoKu = choices.get(i).shimoNoKu();
            fourthPhrases[i] = shimoNoKu.fourth();
            fifthPhrases[i] = shimoNoKu.fifth();
        }

        List<ToriFuda> toriFudas = new ArrayList<>();

        if (karutaStyle == KarutaStyle.KANJI) {
            for (int i = 0; i < choices.size(); i++) {
                toriFudas.add(new ToriFuda(fourthPhrases[i].kanji(), fifthPhrases[i].kanji()));
            }
        } else {
            for (int i = 0; i < choices.size(); i++) {
                toriFudas.add(new ToriFuda(fourthPhrases[i].kana(), fifthPhrases[i].kana()));
            }
        }

        return toriFudas;
    }

    /**
     * @return 正解の歌
     */
    public Karuta correct() {
        return correct;
    }

    /**
     * @return 現在が何問目か
     */
    public String currentPosition() {
        return position.value();
    }

    /**
     * @return 次の問題は存在するか
     */
    public boolean existNext() {
        return existNext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizContent that = (KarutaQuizContent) o;

        return existNext == that.existNext &&
                karutaQuiz.equals(that.karutaQuiz) &&
                correct.equals(that.correct) &&
                choices.equals(that.choices) &&
                position.equals(that.position);
    }

    @Override
    public int hashCode() {
        int result = karutaQuiz.hashCode();
        result = 31 * result + correct.hashCode();
        result = 31 * result + choices.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (existNext ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizContent{" +
                "karutaQuiz=" + karutaQuiz +
                ", correct=" + correct +
                ", choices=" + choices +
                ", position=" + position +
                ", existNext=" + existNext +
                '}';
    }
}
