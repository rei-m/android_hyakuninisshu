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
