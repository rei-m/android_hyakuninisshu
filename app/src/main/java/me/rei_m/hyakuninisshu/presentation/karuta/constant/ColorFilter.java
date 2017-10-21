package me.rei_m.hyakuninisshu.presentation.karuta.constant;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.model.karuta.Color;

public enum ColorFilter implements SpinnerItem {
    ALL(R.string.color_not_select, null),
    BLUE(R.string.color_blue, Color.BLUE),
    PINK(R.string.color_pink, Color.PINK),
    YELLOW(R.string.color_yellow, Color.YELLOW),
    GREEN(R.string.color_green, Color.GREEN),
    ORANGE(R.string.color_orange, Color.ORANGE);

    private final int resId;

    private final Color color;

    ColorFilter(int resId, @Nullable Color color) {
        this.resId = resId;
        this.color = color;
    }

    @Override
    public String code() {
        return (color == null) ? null : color.value();
    }

    @Override
    public String label(Resources res) {
        return res.getString(resId);
    }

    @Nullable
    public Color value() {
        return color;
    }
}
