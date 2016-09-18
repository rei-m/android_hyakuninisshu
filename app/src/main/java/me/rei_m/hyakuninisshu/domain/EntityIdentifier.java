package me.rei_m.hyakuninisshu.domain;

import java.io.Serializable;

public interface EntityIdentifier<ET extends Entity> extends ValueObject, Serializable {

    String getKind();

}
