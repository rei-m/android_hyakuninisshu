/*
 * Copyright (c) 2017. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class Phrase implements ValueObject {

    private final String kana;

    private final String kanji;

    public Phrase(@NonNull String kana, @NonNull String kanji) {
        this.kana = kana;
        this.kanji = kanji;
    }

    public String kana() {
        return kana;
    }

    public String kanji() {
        return kanji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phrase phrase = (Phrase) o;

        if (!kana.equals(phrase.kana)) return false;

        return kanji.equals(phrase.kanji);
    }

    @Override
    public int hashCode() {
        int result = kana.hashCode();
        result = 31 * result + kanji.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Phrase{" +
                "kana='" + kana + '\'' +
                ", kanji='" + kanji + '\'' +
                '}';
    }
}
