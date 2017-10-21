package me.rei_m.hyakuninisshu.presentation.karuta.constant;

import android.content.res.Resources;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaStyle;

public enum KarutaStyleFilter implements SpinnerItem {
    KANA(R.string.display_style_kana, KarutaStyle.KANA),
    KANJI(R.string.display_style_kanji, KarutaStyle.KANJI);

    public static KarutaStyleFilter get(int ordinal) {
        return values()[ordinal];
    }

    private final int resId;

    private final KarutaStyle value;

    KarutaStyleFilter(int resId, KarutaStyle value) {
        this.resId = resId;
        this.value = value;
    }

    @Override
    public String code() {
        return value.value();
    }

    @Override
    public String label(Resources res) {
        return res.getString(resId);
    }

    public KarutaStyle value() {
        return value;
    }
}
