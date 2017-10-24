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

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

public class KarutaIdentifier implements EntityIdentifier, Parcelable {

    private final int value;

    public KarutaIdentifier(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public int position() {
        return value - 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaIdentifier that = (KarutaIdentifier) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "KarutaIdentifier{" +
                "value=" + value +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.value);
    }

    protected KarutaIdentifier(Parcel in) {
        this.value = in.readInt();
    }

    public static final Creator<KarutaIdentifier> CREATOR = new Creator<KarutaIdentifier>() {
        @Override
        public KarutaIdentifier createFromParcel(Parcel source) {
            return new KarutaIdentifier(source);
        }

        @Override
        public KarutaIdentifier[] newArray(int size) {
            return new KarutaIdentifier[size];
        }
    };
}
