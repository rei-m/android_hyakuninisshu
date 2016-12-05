package me.rei_m.hyakuninisshu.infrastructure.database;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import java.util.Date;

@Table
public class ExamSchema {

    public static ExamSchema_Relation relation(OrmaDatabase orma) {
        return orma.relationOfExamSchema();
    }

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public Date tookExamDate;

    @Column
    public int totalQuizCount;

    public ExamWrongKarutaSchema_Relation getWrongKarutas(OrmaDatabase orma) {
        return orma.relationOfExamWrongKarutaSchema().examSchemaEq(this);
    }
}
