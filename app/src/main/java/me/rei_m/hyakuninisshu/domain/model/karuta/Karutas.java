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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.AbstractEntity;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzes;
import me.rei_m.hyakuninisshu.domain.util.ArrayUtil;

public class Karutas {

    private final List<Karuta> values;

    private final List<KarutaIdentifier> ids;

    public Karutas(@NonNull List<Karuta> values) {
        this.values = values;
        this.ids = Observable.fromIterable(values).map(AbstractEntity::identifier).toList().blockingGet();
    }

    public List<Karuta> asList() {
        return Collections.unmodifiableList(values);
    }

    public List<Karuta> asList(@Nullable Color color) {
        if (color == null) {
            return Collections.unmodifiableList(values);
        } else {
            return Observable.fromIterable(values).filter(karuta -> karuta.color().equals(color)).toList().blockingGet();
        }
    }

    public KarutaQuizzes createQuizSet(KarutaIds karutaIds) {

        List<KarutaQuiz> quizzes = new ArrayList<>();

        for (KarutaIdentifier correctKarutaId : karutaIds.asRandomizedList()) {

            List<KarutaIdentifier> choices = new ArrayList<>();

            List<KarutaIdentifier> dupIds = new ArrayList<>(this.ids);
            dupIds.remove(correctKarutaId);

            for (int choiceIndex : ArrayUtil.generateRandomIndexArray(dupIds.size(), KarutaQuiz.CHOICE_SIZE - 1)) {
                choices.add(dupIds.get(choiceIndex));
            }

            int correctPosition = ArrayUtil.generateRandomIndexArray(KarutaQuiz.CHOICE_SIZE, 1)[0];

            choices.add(correctPosition, correctKarutaId);

            KarutaQuiz karutaQuiz = new KarutaQuiz(new KarutaQuizIdentifier(), choices, correctKarutaId);

            quizzes.add(karutaQuiz);
        }

        return new KarutaQuizzes(quizzes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Karutas karutas = (Karutas) o;

        if (!values.equals(karutas.values)) return false;

        return ids.equals(karutas.ids);
    }

    @Override
    public int hashCode() {
        int result = values.hashCode();
        result = 31 * result + ids.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Karutas{" +
                "values=" + values +
                ", ids=" + ids +
                '}';
    }
}
