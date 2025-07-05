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

package me.rei_m.hyakuninisshu.domain.karuta.model

import me.rei_m.hyakuninisshu.domain.AbstractEntity

/**
 * 百人一首の歌エンティティ.
 *
 * @param id ID
 * @param no 歌番号
 * @param creator 作者
 * @param kamiNoKu 上の句
 * @param shimoNoKu 下の句
 * @param kimariji 決まり字
 * @param imageNo 画像番号
 * @param translation 訳
 * @param color 百人一首の色
 */
class Karuta(
    id: KarutaId,
    val no: KarutaNo,
    val creator: String,
    kamiNoKu: KamiNoKu,
    shimoNoKu: ShimoNoKu,
    val kimariji: Kimariji,
    val imageNo: KarutaImageNo,
    val translation: String,
    val color: KarutaColor,
) : AbstractEntity<KarutaId>(id) {
    var kamiNoKu: KamiNoKu = kamiNoKu
        private set

    var shimoNoKu: ShimoNoKu = shimoNoKu
        private set

    fun update(
        kamiNoKu: KamiNoKu,
        shimoNoKu: ShimoNoKu,
    ): Karuta {
        this.kamiNoKu = kamiNoKu
        this.shimoNoKu = shimoNoKu
        return this
    }

    override fun toString(): String =
        """
        Karuta(
            no='$no',
            creator='$creator',
            kamiNoKu='$kamiNoKu',
            shimoNoKu='$shimoNoKu',
            kimariji='$kimariji',
            imageNo='$imageNo',
            translation='$translation',
            color='$color'
        )
    """
}
