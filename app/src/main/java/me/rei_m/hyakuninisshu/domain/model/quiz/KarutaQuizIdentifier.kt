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

package me.rei_m.hyakuninisshu.domain.model.quiz

import android.os.Parcel
import android.os.Parcelable

import java.util.UUID

import me.rei_m.hyakuninisshu.domain.EntityIdentifier

data class KarutaQuizIdentifier(val value: String = UUID.randomUUID().toString()) : EntityIdentifier, Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.value)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<KarutaQuizIdentifier> = object : Parcelable.Creator<KarutaQuizIdentifier> {
            override fun createFromParcel(source: Parcel): KarutaQuizIdentifier = KarutaQuizIdentifier(source.readString())

            override fun newArray(size: Int): Array<KarutaQuizIdentifier?> = arrayOfNulls(size)
        }
    }
}
