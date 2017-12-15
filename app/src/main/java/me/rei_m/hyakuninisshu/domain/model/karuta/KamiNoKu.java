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

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

import static me.rei_m.hyakuninisshu.util.Constants.SPACE;

/**
 * 上の句.
 */
public class KamiNoKu extends AbstractEntity<KamiNoKu, KamiNoKuIdentifier> implements Parcelable {

    private final Phrase first;

    private final Phrase second;

    private final Phrase third;

    public KamiNoKu(@NonNull KamiNoKuIdentifier identifier,
                    @NonNull Phrase first,
                    @NonNull Phrase second,
                    @NonNull Phrase third) {
        super(identifier);
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Phrase first() {
        return first;
    }

    public Phrase second() {
        return second;
    }

    public Phrase third() {
        return third;
    }

    public String kanji() {
        return first.kanji() + SPACE + second.kanji() + SPACE + third.kanji();
    }

    public String kana() {
        return first.kana() + SPACE + second.kana() + SPACE + third.kana();
    }

    @Override
    public String toString() {
        return "KamiNoKu{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                "} " + super.toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.identifier(), flags);
        dest.writeParcelable(this.first, flags);
        dest.writeParcelable(this.second, flags);
        dest.writeParcelable(this.third, flags);
    }

    protected KamiNoKu(Parcel in) {
        super(in.readParcelable(KamiNoKuIdentifier.class.getClassLoader()));
        this.first = in.readParcelable(Phrase.class.getClassLoader());
        this.second = in.readParcelable(Phrase.class.getClassLoader());
        this.third = in.readParcelable(Phrase.class.getClassLoader());
    }

    public static final Creator<KamiNoKu> CREATOR = new Creator<KamiNoKu>() {
        @Override
        public KamiNoKu createFromParcel(Parcel source) {
            return new KamiNoKu(source);
        }

        @Override
        public KamiNoKu[] newArray(int size) {
            return new KamiNoKu[size];
        }
    };
}
