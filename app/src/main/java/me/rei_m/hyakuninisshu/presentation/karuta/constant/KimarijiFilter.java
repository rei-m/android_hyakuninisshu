package me.rei_m.hyakuninisshu.presentation.karuta.constant;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji;

public enum KimarijiFilter implements SpinnerItem {
    ALL(R.string.kimariji_not_select, "all", null),
    ONE(R.string.kimariji_1, "kimariji_1", Kimariji.ONE),
    TWO(R.string.kimariji_2, "kimariji_2", Kimariji.TWO),
    THREE(R.string.kimariji_3, "kimariji_3", Kimariji.THREE),
    FOUR(R.string.kimariji_4, "kimariji_4", Kimariji.FOUR),
    FIVE(R.string.kimariji_5, "kimariji_5", Kimariji.FIVE),
    SIX(R.string.kimariji_6, "kimariji_6", Kimariji.SIX);

    private final int resId;

    private final String code;

    private final Kimariji value;

    KimarijiFilter(int resId, String code, @Nullable Kimariji value) {
        this.resId = resId;
        this.code = code;
        this.value = value;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String label(Resources res) {
        return res.getString(resId);
    }

    @Nullable
    public Kimariji value() {
        return value;
    }
}
