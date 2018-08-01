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

package me.rei_m.hyakuninisshu.infrastructure.database

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
import com.squareup.moshi.Json

@Table
data class KarutaSchema(
        @Setter("id") @PrimaryKey(autoincrement = false) @Json(name = "id") var id: Long,
        @Setter("creator") @Column @Json(name = "creator") var creator: String,
        @Setter("firstKana") @Column @Json(name = "first_kana") var firstKana: String,
        @Setter("firstKanji") @Column @Json(name = "first_kanji") var firstKanji: String,
        @Setter("secondKana") @Column @Json(name = "second_kana") var secondKana: String,
        @Setter("secondKanji") @Column @Json(name = "second_kanji") var secondKanji: String,
        @Setter("thirdKana") @Column @Json(name = "third_kana") var thirdKana: String,
        @Setter("thirdKanji") @Column @Json(name = "third_kanji") var thirdKanji: String,
        @Setter("fourthKana") @Column @Json(name = "fourth_kana") var fourthKana: String,
        @Setter("fourthKanji") @Column @Json(name = "fourth_kanji") var fourthKanji: String,
        @Setter("fifthKana") @Column @Json(name = "fifth_kana") var fifthKana: String,
        @Setter("fifthKanji") @Column @Json(name = "fifth_kanji") var fifthKanji: String,
        @Setter("kimariji") @Column @Json(name = "kimariji") var kimariji: Int,
        @Setter("imageNo") @Column @Json(name = "image_no") var imageNo: String,
        @Setter("translation") @Column @Json(name = "translation") var translation: String,
        @Setter("color") @Column @Json(name = "color") var color: String,
        @Setter("colorNo") @Column @Json(name = "color_no") var colorNo: Int,
        @Setter("isEdited") @Column var isEdited: Boolean? = false
) {
    companion object {
        fun relation(orma: OrmaDatabase): KarutaSchema_Relation {
            return orma.relationOfKarutaSchema()
        }
    }
}
