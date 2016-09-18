package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class KarutaPart implements ValueObject {

    private final String kana;

    private final String kanji;

    public KarutaPart(@NonNull String kana, @NonNull String kanji) {
        this.kana = kana;
        this.kanji = kanji;
    }

    public String getKana() {
        return kana;
    }

    public String getKanji() {
        return kanji;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        KarutaPart that = (KarutaPart) o;

        return kana.equals(that.kana) && kanji.equals(that.kanji);
    }

    @Override
    public int hashCode() {
        int result = kana.hashCode();
        result = 31 * result + kanji.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "KarutaPart{" +
                "kana='" + kana + '\'' +
                ", kanji='" + kanji + '\'' +
                '}';
    }
}
