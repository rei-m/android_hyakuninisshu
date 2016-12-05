package me.rei_m.hyakuninisshu.domain.karuta.model;

import java.util.List;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class KarutaQuizContents implements ValueObject {

    public final List<KarutaIdentifier> choiceList;

    public final KarutaIdentifier collectId;

    public KarutaQuizContents(List<KarutaIdentifier> choiceList, KarutaIdentifier collectId) {
        this.choiceList = choiceList;
        this.collectId = collectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizContents that = (KarutaQuizContents) o;

        return choiceList.equals(that.choiceList) && collectId.equals(that.collectId);

    }

    @Override
    public int hashCode() {
        int result = choiceList.hashCode();
        result = 31 * result + collectId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizContents{" +
                "choiceList=" + choiceList +
                ", collectId=" + collectId +
                '}';
    }
}
