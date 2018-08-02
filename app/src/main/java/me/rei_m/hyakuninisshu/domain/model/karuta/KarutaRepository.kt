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

import io.reactivex.Completable
import io.reactivex.Single

/**
 * 歌リポジトリ.
 */
interface KarutaRepository {

    /**
     * 歌セットを初期化する.
     *
     * @return Completable
     */
    fun initialize(): Completable

    /**
     * 歌を取得する.
     *
     * @param identifier 歌ID
     * @return 歌
     */
    fun findBy(identifier: KarutaIdentifier): Single<Karuta>

    /**
     * 歌コレクションを取得する.
     *
     * @return 歌コレクション
     */
    fun list(): Single<Karutas>

    /**
     * 歌コレクションを取得する.
     *
     * @param color 色
     * @return 歌コレクション
     */
    fun list(color: Color?): Single<Karutas>

    /**
     * 歌IDコレクションを取得する.
     *
     * @return 歌IDコレクション
     */
    fun findIds(): Single<KarutaIds>

    /**
     * 歌IDコレクションを取得する.
     *
     * @param fromIdentifier 取得対象のIDのFrom
     * @param toIdentifier   取得対象のIDのTo
     * @param color          取得対象の歌の色
     * @param kimariji       取得対象の決まり字
     * @return 歌IDコレクション
     */
    fun findIds(fromIdentifier: KarutaIdentifier,
                toIdentifier: KarutaIdentifier,
                color: Color?,
                kimariji: Kimariji?): Single<KarutaIds>

    /**
     * 歌を永続化する,
     *
     * @param karuta 歌
     * @return Completable
     */
    fun store(karuta: Karuta): Completable
}
