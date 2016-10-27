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
}
