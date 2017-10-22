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

public class KarutaIds {

    private final List<KarutaIdentifier> values;

    public KarutaIds(@NonNull List<KarutaIdentifier> values) {
        this.values = values;
    }

    public List<KarutaIdentifier> asList() {
        return Collections.unmodifiableList(values);
    }

    public List<KarutaIdentifier> asRandomizedList() {
        List<KarutaIdentifier> correctKarutaIdList = new ArrayList<>();
        int[] collectKarutaIndexList = ArrayUtil.generateRandomIndexArray(values.size(), values.size());
        for (int i : collectKarutaIndexList) {
            correctKarutaIdList.add(values.get(i));
        }
        return correctKarutaIdList;
    }

    public int size() {
        return values.size();
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
