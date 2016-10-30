package me.rei_m.hyakuninisshu.presentation.karuta.constant;

import android.content.res.Resources;

import me.rei_m.hyakuninisshu.R;

public enum Kimariji implements SpinnerItem {
    ALL(R.string.kimariji_not_select, "all", 0),
    KIMARIJI_ONE(R.string.kimariji_1, "kimariji_1", 1),
    KIMARIJI_TWO(R.string.kimariji_2, "kimariji_2", 2),
    KIMARIJI_THREE(R.string.kimariji_3, "kimariji_3", 3),
    KIMARIJI_FOUR(R.string.kimariji_4, "kimariji_4", 4),
    KIMARIJI_FIVE(R.string.kimariji_5, "kimariji_5", 5),
    KIMARIJI_SIX(R.string.kimariji_6, "kimariji_6", 6);

    private final int resId;

    private final String code;

    private final int position;

    Kimariji(int resId, String code, int position) {
        this.resId = resId;
        this.code = code;
        this.position = position;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel(Resources res) {
        return res.getString(resId);
    }

    public int getPosition() {
        return position;
    }
}
