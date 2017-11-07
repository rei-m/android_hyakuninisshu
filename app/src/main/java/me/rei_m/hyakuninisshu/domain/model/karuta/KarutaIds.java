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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.util.ArrayUtil;

/**
 * 歌IDのコレクション.
 */
public class KarutaIds {

    private final List<KarutaIdentifier> values;

    public KarutaIds(@NonNull List<KarutaIdentifier> values) {
        this.values = values;
    }

    /**
     * @return 歌IDのリスト
     */
    public List<KarutaIdentifier> asList() {
        return Collections.unmodifiableList(values);
    }

    /**
     * @return ランダムにソートされた歌IDのリスト
     */
    public List<KarutaIdentifier> asRandomizedList() {
        List<KarutaIdentifier> correctKarutaIdList = new ArrayList<>();
        int[] collectKarutaIndexList = ArrayUtil.generateRandomIndexArray(values.size(), values.size());
        for (int i : collectKarutaIndexList) {
            correctKarutaIdList.add(values.get(i));
        }
        return correctKarutaIdList;
    }

    /**
     * @return 保持している歌IDの数
     */
    public int size() {
        return values.size();
    }

    /**
     * 指定の歌IDを含んでいるか確認する.
     *
     * @param karutaId 確認対象の歌ID
     * @return {@code true} 含んでいる場合, {@code false} 含んでいない場合
     */
    public boolean contains(@NonNull KarutaIdentifier karutaId) {
        return values.contains(karutaId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaIds karutaIds = (KarutaIds) o;

        return values.equals(karutaIds.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        return "KarutaIds{" +
                "values=" + values +
                '}';
    }
}
