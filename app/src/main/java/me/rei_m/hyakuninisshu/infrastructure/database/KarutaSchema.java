package me.rei_m.hyakuninisshu.infrastructure.database;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class KarutaSchema {

    public static KarutaSchema_Relation relation(OrmaDatabase orma) {
        return orma.relationOfKarutaSchema().orderByIdAsc();
    }

    @PrimaryKey(auto = false)
    public int id;

    @Column
    public String firstKana;

    @Column
    public String firstKanji;

    @Column
    public String secondKana;

    @Column
    public String secondKanji;

    @Column
    public String thirdKana;

    @Column
    public String thirdKanji;

    @Column
    public String fourthKana;

    @Column
    public String fourthKanji;

    @Column
    public String fifthKana;

    @Column
    public String fifthKanji;

    @Column
    public int kimariji;

    @Column
    public String imageNo;
}
