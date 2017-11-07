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

package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * 歌リポジトリ.
 */
public interface KarutaRepository {

    /**
     * 歌セットを初期化する.
     *
     * @return Completable
     */
    Completable initialize();

    /**
     * 歌を取得する.
     *
     * @param identifier 歌ID
     * @return 歌
     */
    Single<Karuta> findBy(@NonNull KarutaIdentifier identifier);

    /**
     * 歌コレクションを取得する.
     *
     * @return 歌コレクション
     */
    Single<Karutas> list();

    /**
     * 歌IDコレクションを取得する.
     *
     * @return 歌IDコレクション
     */
    Single<KarutaIds> findIds();

    /**
     * 歌IDコレクションを取得する.
     *
     * @param fromIdentifier 取得対象のIDのFrom
     * @param toIdentifier   取得対象のIDのTo
     * @param color          取得対象の歌の色
     * @param kimariji       取得対象の決まり字
     * @return 歌IDコレクション
     */
    Single<KarutaIds> findIds(@NonNull KarutaIdentifier fromIdentifier,
                              @NonNull KarutaIdentifier toIdentifier,
                              @Nullable Color color,
                              @Nullable Kimariji kimariji);

    /**
     * 歌を永続化する,
     *
     * @param karuta 歌
     * @return Completable
     */
    Completable store(@NonNull Karuta karuta);
}
