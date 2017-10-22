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

package me.rei_m.hyakuninisshu.infrastructure.database;

import android.support.annotation.NonNull;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.List;

public class KarutaJsonAdaptor {

    private KarutaJsonAdaptor() {
    }

    public static List<KarutaSchema> convert(@NonNull String jsonString) throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<KarutaList> jsonAdapter = moshi.adapter(KarutaList.class);
        KarutaList karutaList = jsonAdapter.fromJson(jsonString);
        return karutaList.karutaList;
    }

    private static class KarutaList {
        @Json(name = "karuta_list")
        List<KarutaSchema> karutaList;
    }
}
