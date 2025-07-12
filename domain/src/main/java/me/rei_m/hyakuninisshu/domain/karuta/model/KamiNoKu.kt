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

import me.rei_m.hyakuninisshu.domain.ValueObject

/**
 * 上の句.
 *
 * @param karutaNo 歌番号
 * @param shoku 初句
 * @param niku 二句
 * @param sanku 三句
 */
data class KamiNoKu(
    val karutaNo: KarutaNo,
    val shoku: Verse,
    val niku: Verse,
    val sanku: Verse,
) : ValueObject
