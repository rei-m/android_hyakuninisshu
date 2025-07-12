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

package me.rei_m.hyakuninisshu.infrastructure.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "karuta_table")
class KarutaData(
    @PrimaryKey(autoGenerate = false) @param:Json(name = "no") var no: Int,
    @param:Json(name = "creator") var creator: String,
    @ColumnInfo(name = "first_kana") @param:Json(name = "first_kana") var firstKana: String,
    @ColumnInfo(name = "first_kanji") @param:Json(name = "first_kanji") var firstKanji: String,
    @ColumnInfo(name = "second_kana") @param:Json(name = "second_kana") var secondKana: String,
    @ColumnInfo(name = "second_kanji") @param:Json(name = "second_kanji") var secondKanji: String,
    @ColumnInfo(name = "third_kana") @param:Json(name = "third_kana") var thirdKana: String,
    @ColumnInfo(name = "third_kanji") @param:Json(name = "third_kanji") var thirdKanji: String,
    @ColumnInfo(name = "fourth_kana") @param:Json(name = "fourth_kana") var fourthKana: String,
    @ColumnInfo(name = "fourth_kanji") @param:Json(name = "fourth_kanji") var fourthKanji: String,
    @ColumnInfo(name = "fifth_kana") @param:Json(name = "fifth_kana") var fifthKana: String,
    @ColumnInfo(name = "fifth_kanji") @param:Json(name = "fifth_kanji") var fifthKanji: String,
    @param:Json(name = "kimariji") var kimariji: Int,
    @ColumnInfo(name = "image_no") @param:Json(name = "image_no") var imageNo: String,
    @param:Json(name = "translation") var translation: String,
    @param:Json(name = "color") var color: String,
    @ColumnInfo(name = "color_no") @param:Json(name = "color_no") var colorNo: Int,
    @ColumnInfo(name = "torifuda") @param:Json(name = "torifuda") var torifuda: String,
    @ColumnInfo(name = "is_edited") var isEdited: Boolean? = false
)
