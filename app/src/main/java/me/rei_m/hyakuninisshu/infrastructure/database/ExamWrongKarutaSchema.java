package me.rei_m.hyakuninisshu.infrastructure.database;

import com.github.gfx.android.orma.SingleAssociation;
import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class ExamWrongKarutaSchema {

    public static ExamWrongKarutaSchema_Relation relation(OrmaDatabase orma) {
        return orma.relationOfExamWrongKarutaSchema();
    }

    @Column(indexed = true)
    public SingleAssociation<ExamSchema> examSchema;

    @Column
    public long karutaId;
}
