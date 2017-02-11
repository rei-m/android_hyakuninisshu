package me.rei_m.hyakuninisshu.presentation.karuta.constant;

import android.content.res.Resources;

import me.rei_m.hyakuninisshu.R;

public enum Color implements SpinnerItem {
    ALL(R.string.color_not_select, null),
    BLUE(R.string.color_blue, "blue"),
    PINK(R.string.color_pink, "pink"),
    YELLOW(R.string.color_yellow, "yellow"),
    GREEN(R.string.color_green, "green"),
    ORANGE(R.string.color_orange, "orange");

    private final int resId;

    private final String code;

    Color(int resId, String code) {
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
