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

public class KamiNoKuIdentifier implements EntityIdentifier, Parcelable {

    private final int value;

    public KamiNoKuIdentifier(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KamiNoKuIdentifier that = (KamiNoKuIdentifier) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "KamiNoKuIdentifier{" +
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

    protected KamiNoKuIdentifier(Parcel in) {
        this.value = in.readInt();
    }

    public static final Creator<KamiNoKuIdentifier> CREATOR = new Creator<KamiNoKuIdentifier>() {
        @Override
        public KamiNoKuIdentifier createFromParcel(Parcel source) {
            return new KamiNoKuIdentifier(source);
        }

        @Override
        public KamiNoKuIdentifier[] newArray(int size) {
            return new KamiNoKuIdentifier[size];
        }
    };
}
