package me.rei_m.hyakuninisshu.infrastructure.database;

import android.support.annotation.Nullable;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;
import com.squareup.moshi.Json;

@Table
public class KarutaSchema {

    public static KarutaSchema_Relation relation(OrmaDatabase orma) {
        return orma.relationOfKarutaSchema();
    }

    @PrimaryKey(auto = false)
    @Json(name = "id")
    public long id;

    @Column
    @Json(name = "creator")
    public String creator;

    @Column
    @Json(name = "first_kana")
    public String firstKana;

    @Column
    @Json(name = "first_kanji")
    public String firstKanji;

    @Column
    @Json(name = "second_kana")
    public String secondKana;

    @Column
    @Json(name = "second_kanji")
    public String secondKanji;

    @Column
    @Json(name = "third_kana")
    public String thirdKana;

    @Column
    @Json(name = "third_kanji")
    public String thirdKanji;

    @Column
    @Json(name = "fourth_kana")
    public String fourthKana;

    @Column
    @Json(name = "fourth_kanji")
    public String fourthKanji;

    @Column
    @Json(name = "fifth_kana")
    public String fifthKana;

    @Column
    @Json(name = "fifth_kanji")
    public String fifthKanji;

    @Column
    @Json(name = "kimariji")
    public int kimariji;

    @Column
    @Json(name = "image_no")
    public String imageNo;

    @Column
    @Json(name = "translation")
    @Nullable
    public String translation;

    @Column
    @Json(name = "color")
    @Nullable
    public String color;

    @Column
    @Json(name = "color_no")
    @Nullable
    public int colorNo;

    @Override
    public String toString() {
        return "KarutaSchema{" +
                "id=" + id +
                ", creator='" + creator + '\'' +
                ", firstKana='" + firstKana + '\'' +
                ", firstKanji='" + firstKanji + '\'' +
                ", secondKana='" + secondKana + '\'' +
                ", secondKanji='" + secondKanji + '\'' +
                ", thirdKana='" + thirdKana + '\'' +
                ", thirdKanji='" + thirdKanji + '\'' +
                ", fourthKana='" + fourthKana + '\'' +
                ", fourthKanji='" + fourthKanji + '\'' +
                ", fifthKana='" + fifthKana + '\'' +
                ", fifthKanji='" + fifthKanji + '\'' +
                ", kimariji=" + kimariji +
                ", imageNo='" + imageNo + '\'' +
                ", translation='" + translation + '\'' +
                ", color='" + color + '\'' +
                ", colorNo=" + colorNo +
                '}';
    }
}
