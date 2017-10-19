package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.util.ArrayUtil;

public class Karutas {

    private final List<Karuta> values;

    public Karutas(final List<Karuta> values) {
        this.values = values;
    }

    public List<Karuta> asList() {
        return Collections.unmodifiableList(values);
    }

    public List<Karuta> asList(@Nullable String color) {
        if (color == null) {
            return Collections.unmodifiableList(values);
        } else {
            List<Karuta> filteredKarutas = Observable.fromIterable(values).filter(karuta -> karuta.color().equals(new Color(color))).toList().blockingGet();
            return Collections.unmodifiableList(filteredKarutas);
        }
    }

    public List<KarutaQuiz> createQuizSet(KarutaIds karutaIds) {

        List<KarutaQuiz> quizzes = new ArrayList<>();

        for (KarutaIdentifier correctKarutaId : karutaIds.asRandomizedList()) {

            List<KarutaIdentifier> choices = new ArrayList<>();

            long correctIndex = correctKarutaId.value() - 1;

            for (int targetIndex : ArrayUtil.generateRandomArray(values.size() - 1, 3)) {
                if (targetIndex == correctIndex) {
                    choices.add(values.get(values.size() - 1).identifier());
                } else {
                    choices.add(values.get(targetIndex).identifier());
                }
            }

            int[] correctPosition = ArrayUtil.generateRandomArray(4, 1);

            choices.add(correctPosition[0], correctKarutaId);

            KarutaQuiz karutaQuiz = new KarutaQuiz(new KarutaQuizIdentifier(), choices, correctKarutaId);
            quizzes.add(karutaQuiz);
        }

        return quizzes;
    }
}
