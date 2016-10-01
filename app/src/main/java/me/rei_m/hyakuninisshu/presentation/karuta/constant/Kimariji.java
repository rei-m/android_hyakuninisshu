package me.rei_m.hyakuninisshu.presentation.karuta.constant;

import android.content.res.Resources;

import me.rei_m.hyakuninisshu.R;

public enum Kimariji implements SpinnerItem {
    ALL(R.string.kimariji_not_select, "all"),
    ONE_TO_TEN(R.string.kimariji_1, "kimariji_1"),
    ELEVEN_TO_TWENTY(R.string.kimariji_2, "kimariji_2"),
    TWENTY_ONE_TO_THIRTY(R.string.kimariji_3, "kimariji_3"),
    THIRTY_ONE_TO_FORTY(R.string.kimariji_4, "kimariji_4"),
    FORTY_ONE_TO_FIFTY(R.string.kimariji_5, "kimariji_5"),
    FIFTY_ONE_TO_SIXTY(R.string.kimariji_6, "kimariji_6");

    private int resId;

    private String code;

    Kimariji(int resId, String code) {
        this.resId = resId;
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel(Resources res) {
        return res.getString(resId);
    }
}
