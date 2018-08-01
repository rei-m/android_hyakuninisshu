/*
 * Copyright (c) 2018. Rei Matsushita
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

package me.rei_m.hyakuninisshu.domain.model.karuta

import android.os.Parcel
import android.os.Parcelable

import me.rei_m.hyakuninisshu.domain.EntityIdentifier

data class KarutaIdentifier(val value: Int) : EntityIdentifier, Parcelable {

    val position: Int =  value - 1

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.value)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<KarutaIdentifier> = object : Parcelable.Creator<KarutaIdentifier> {
            override fun createFromParcel(source: Parcel): KarutaIdentifier = KarutaIdentifier(source.readInt())

            override fun newArray(size: Int): Array<KarutaIdentifier?> = arrayOfNulls(size)
        }
    }
}
