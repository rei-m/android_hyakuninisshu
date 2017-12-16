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

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

/**
 * 歌の句.
 */
public class Phrase implements ValueObject, Parcelable {

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

        return kana.equals(phrase.kana) &&
                kanji.equals(phrase.kanji);
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kana);
        dest.writeString(this.kanji);
    }

    protected Phrase(Parcel in) {
        this.kana = in.readString();
        this.kanji = in.readString();
    }

    public static final Creator<Phrase> CREATOR = new Creator<Phrase>() {
        @Override
        public Phrase createFromParcel(Parcel source) {
            return new Phrase(source);
        }

        @Override
        public Phrase[] newArray(int size) {
            return new Phrase[size];
        }
    };
}
