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

/**
 * 歌リポジトリ.
 */
interface KarutaRepository {
    /**
     * 歌セットを初期化する.
     */
    suspend fun initialize()

    /**
     * 指定した番号の歌を取得する
     *
     * @param karutaNo 歌番号
     *
     * @return 歌
     */
    suspend fun findByNo(karutaNo: KarutaNo): Karuta

    /**
     * すべての歌のリストを取得する
     *
     * @return 歌のリスト
     */
    suspend fun findAll(): List<Karuta>

    /**
     * 指定した条件の歌のリストを取得する
     *
     * @param fromNo 開始の歌番号
     * @param toNo 終了の歌番号
     * @param kimarijis 決まり字のリスト
     * @param colors 色のリスト
     *
     * @return 条件に該当する歌のリスト
     */
    suspend fun findAllWithCondition(
        fromNo: KarutaNo,
        toNo: KarutaNo,
        kimarijis: List<Kimariji>,
        colors: List<KarutaColor>,
    ): List<Karuta>

    /**
     * 指定した番号の歌のリストを取得する.
     *
     * @param karutaNoList 歌番号のリスト
     *
     * @return 条件に該当する歌のリスト
     */
    suspend fun findAllWithNo(karutaNoList: List<KarutaNo>): List<Karuta>

    /**
     * 指定した歌を保存する.
     *
     * @param karuta 歌
     */
    suspend fun save(karuta: Karuta)
}
