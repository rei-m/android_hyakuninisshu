package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class KarutaQuiz implements ValueObject {

    private final List<BottomPhrase> bottomPhraseList;

    private final Karuta collectKaruta;

    public KarutaQuiz(@NonNull List<BottomPhrase> bottomPhraseList,
                      @NonNull Karuta collectKaruta) {
        this.bottomPhraseList = bottomPhraseList;
        this.collectKaruta = collectKaruta;
    }

    public TopPhrase getQuizPhrase() {
        return collectKaruta.getTopPhrase();
    }

    public List<BottomPhrase> getQuizChoices() {
        return bottomPhraseList;
    }

    public KarutaQuizAnswer start(@NonNull Date startDate) {
        return new KarutaQuizAnswer(collectKaruta, startDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuiz that = (KarutaQuiz) o;

        return bottomPhraseList.equals(that.bottomPhraseList) && collectKaruta.equals(that.collectKaruta);
    }

    @Override
    public int hashCode() {
        int result = bottomPhraseList.hashCode();
        result = 31 * result + collectKaruta.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuiz{" +
                "bottomPhraseList=" + bottomPhraseList +
                ", collectKaruta=" + collectKaruta +
                '}';
    }
}
