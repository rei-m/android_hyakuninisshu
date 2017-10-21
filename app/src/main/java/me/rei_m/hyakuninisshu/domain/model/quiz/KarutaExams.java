package me.rei_m.hyakuninisshu.domain.model.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;

public class KarutaExams {

    private final List<KarutaExam> values;

    public KarutaExams(final List<KarutaExam> values) {
        this.values = values;
    }

    public List<KarutaExam> asList() {
        return Collections.unmodifiableList(values);
    }

    public KarutaExam recent() {
        return (values.isEmpty()) ? null : values.get(0);
    }

    public KarutaIds totalWrongKarutaIds() {
        return Observable.fromIterable(values).reduce(new ArrayList<KarutaIdentifier>(), (karutaIdList, karutaExam) -> {
            for (KarutaIdentifier wrongKarutaId : karutaExam.result().wrongKarutaIds().asList()) {
                if (!karutaIdList.contains(wrongKarutaId)) {
                    karutaIdList.add(wrongKarutaId);
                }
            }
            return karutaIdList;
        }).map(KarutaIds::new).blockingGet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaExams that = (KarutaExams) o;

        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        return "KarutaExams{" +
                "values=" + values +
                '}';
    }
}
