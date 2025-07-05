/*
 * Copyright (c) 2025. Rei Matsushita
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

package me.rei_m.hyakuninisshu.state.material.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

/**
 * 歌の資料表示用.
 *
 * @param no 番号
 * @param noTxt 番号の表示用テキスト
 * @param kimariji 決まり字
 * @param kimarijiTxt 決まり字の表示用テキスト
 * @param creator 作者
 * @param shokuKanji 初句の漢字
 * @param shokuKana 初句のかな
 * @param nikuKanji 二句の漢字
 * @param nikuKana 二句のかな
 * @param sankuKanji 三句の漢字
 * @param sankuKana 三句のかな
 * @param shikuKanji 四句の漢字
 * @param shikuKana 四句のかな
 * @param gokuKanji 結句の漢字
 * @param gokuKana 結句のかな
 * @param translation 訳
 * @param imageResId 画像のリソースID
 */
data class Material(
    val no: Int,
    val noTxt: String,
    val kimariji: Int,
    val kimarijiTxt: String,
    val creator: String,
    val shokuKanji: String,
    val shokuKana: String,
    val nikuKanji: String,
    val nikuKana: String,
    val sankuKanji: String,
    val sankuKana: String,
    val shikuKanji: String,
    val shikuKana: String,
    val gokuKanji: String,
    val gokuKana: String,
    val translation: String,
    @param:DrawableRes val imageResId: Int,
) : Parcelable {
    val kamiNoKuKanji: String = "$shokuKanji　$nikuKanji　$sankuKanji"
    val kamiNoKuKana: String = "$shokuKana　$nikuKana　$sankuKana"
    val shimoNoKuKanji: String = "$shikuKanji　$gokuKanji"
    val shimoNoKuKana: String = "$shikuKana　$gokuKana"

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
    )

    override fun writeToParcel(
        parcel: Parcel,
        flags: Int,
    ) {
        parcel.writeInt(no)
        parcel.writeString(noTxt)
        parcel.writeInt(kimariji)
        parcel.writeString(kimarijiTxt)
        parcel.writeString(creator)
        parcel.writeString(shokuKanji)
        parcel.writeString(shokuKana)
        parcel.writeString(nikuKanji)
        parcel.writeString(nikuKana)
        parcel.writeString(sankuKanji)
        parcel.writeString(sankuKana)
        parcel.writeString(shikuKanji)
        parcel.writeString(shikuKana)
        parcel.writeString(gokuKanji)
        parcel.writeString(gokuKana)
        parcel.writeString(translation)
        parcel.writeInt(imageResId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Material> {
        override fun createFromParcel(parcel: Parcel): Material = Material(parcel)

        override fun newArray(size: Int): Array<Material?> = arrayOfNulls(size)
    }
}
