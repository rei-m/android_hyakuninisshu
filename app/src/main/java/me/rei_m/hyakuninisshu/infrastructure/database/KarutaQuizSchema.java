package me.rei_m.hyakuninisshu.infrastructure.database;

import android.support.annotation.Nullable;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import java.util.Date;

@Table
public class KarutaQuizSchema {

    public static KarutaQuizSchema_Relation relation(OrmaDatabase orma) {
        return orma.relationOfKarutaQuizSchema();
    }

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column(indexed = true)
    public String quizId;

    @Column
    public long collectId;

    @Column
    @Nullable
    public Date startDate;

    @Column
    public boolean isCollect;

    @Column
    public long answerTime;

    public KarutaQuizChoiceSchema_Relation getChoices(OrmaDatabase orma) {
        return orma.relationOfKarutaQuizChoiceSchema()
                .karutaQuizSchemaEq(this)
                .orderByOrderNoAsc();
    }
}
