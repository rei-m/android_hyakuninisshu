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

package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

public class KarutaQuizIdentifier implements EntityIdentifier, Parcelable {

    private final String value;

    public KarutaQuizIdentifier() {
        this.value = UUID.randomUUID().toString();
    }

    public KarutaQuizIdentifier(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizIdentifier that = (KarutaQuizIdentifier) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "KarutaQuizIdentifier{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value);
    }

    protected KarutaQuizIdentifier(Parcel in) {
        this.value = in.readString();
    }

    @SuppressWarnings("unused")
    public static final Creator<KarutaQuizIdentifier> CREATOR = new Creator<KarutaQuizIdentifier>() {
        @Override
        public KarutaQuizIdentifier createFromParcel(Parcel source) {
            return new KarutaQuizIdentifier(source);
        }

        @Override
        public KarutaQuizIdentifier[] newArray(int size) {
            return new KarutaQuizIdentifier[size];
        }
    };
}
