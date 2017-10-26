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

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

public class KarutaExamIdentifier implements EntityIdentifier, Parcelable {

    private final long value;

    public KarutaExamIdentifier(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaExamIdentifier that = (KarutaExamIdentifier) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }

    @Override
    public String toString() {
        return "KarutaExamIdentifier{" +
                "value=" + value +
                '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.value);
    }

    protected KarutaExamIdentifier(Parcel in) {
        this.value = in.readLong();
    }

    @SuppressWarnings("unused")
    public static final Creator<KarutaExamIdentifier> CREATOR = new Creator<KarutaExamIdentifier>() {
        @Override
        public KarutaExamIdentifier createFromParcel(Parcel source) {
            return new KarutaExamIdentifier(source);
        }

        @Override
        public KarutaExamIdentifier[] newArray(int size) {
            return new KarutaExamIdentifier[size];
        }
    };
}
