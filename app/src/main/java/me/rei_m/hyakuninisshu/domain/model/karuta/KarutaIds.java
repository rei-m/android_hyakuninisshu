package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.util.ArrayUtil;

public class KarutaIds {

    private final List<KarutaIdentifier> values;

    public KarutaIds(@NonNull List<KarutaIdentifier> values) {
        this.values = values;
    }

    public List<KarutaIdentifier> asRandomizedList() {
        List<KarutaIdentifier> correctKarutaIdList = new ArrayList<>();
        int[] collectKarutaIndexList = ArrayUtil.generateRandomArray(values.size(), values.size());
        for (int i : collectKarutaIndexList) {
            correctKarutaIdList.add(values.get(i));
        }
        return correctKarutaIdList;
    }

    public int size() {
        return values.size();
    }
}
