package me.rei_m.hyakuninisshu.infrastructure.database;

import com.github.gfx.android.orma.SingleAssociation;
import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class KarutaQuizChoiceSchema {

    public static KarutaQuizChoiceSchema_Relation relation(OrmaDatabase orma) {
        return orma.relationOfKarutaQuizChoiceSchema();
    }

    @Column(indexed = true)
    public SingleAssociation<KarutaQuizSchema> karutaQuizSchema;

    @Column
    public long karutaId;

    @Column(indexed = true)
    public long orderNo;
}
