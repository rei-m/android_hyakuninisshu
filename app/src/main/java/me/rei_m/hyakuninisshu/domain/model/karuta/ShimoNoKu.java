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
 * 下の句.
 */
public class ShimoNoKu extends AbstractEntity<ShimoNoKu, ShimoNoKuIdentifier> implements Parcelable {

    private final Phrase fourth;

    private final Phrase fifth;

    public ShimoNoKu(@NonNull ShimoNoKuIdentifier identifier,
                     @NonNull Phrase fourth,
                     @NonNull Phrase fifth) {
        super(identifier);
        this.fourth = fourth;
        this.fifth = fifth;
    }

    public Phrase fourth() {
        return fourth;
    }

    public Phrase fifth() {
        return fifth;
    }

    public String kanji() {
        return fourth.kanji() + SPACE + fifth.kanji();
    }

    public String kana() {
        return fourth.kana() + SPACE + fifth.kana();
    }

    @Override
    public String toString() {
        return "ShimoNoKu{" +
                "fourth=" + fourth +
                ", fifth=" + fifth +
                "} " + super.toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.identifier(), flags);
        dest.writeParcelable(this.fourth, flags);
        dest.writeParcelable(this.fifth, flags);
    }

    protected ShimoNoKu(Parcel in) {
        super(in.readParcelable(ShimoNoKuIdentifier.class.getClassLoader()));
        this.fourth = in.readParcelable(Phrase.class.getClassLoader());
        this.fifth = in.readParcelable(Phrase.class.getClassLoader());
    }

    public static final Parcelable.Creator<ShimoNoKu> CREATOR = new Parcelable.Creator<ShimoNoKu>() {
        @Override
        public ShimoNoKu createFromParcel(Parcel source) {
            return new ShimoNoKu(source);
        }

        @Override
        public ShimoNoKu[] newArray(int size) {
            return new ShimoNoKu[size];
        }
    };
}
