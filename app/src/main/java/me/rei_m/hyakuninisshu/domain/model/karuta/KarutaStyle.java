package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public enum KarutaStyle implements ValueObject {
    KANA("kana"),
    KANJI("kanji");

    KarutaStyle(@NonNull String value) {
        this.value = value;
    }

    private final String value;

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "KarutaStyle{" +
                "value='" + value + '\'' +
                "} " + super.toString();
    }
}
