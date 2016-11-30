package me.rei_m.hyakuninisshu.presentation.karuta.constant;

import android.content.res.Resources;

import me.rei_m.hyakuninisshu.R;

public enum KarutaStyle implements SpinnerItem {
    KANA(R.string.display_style_kana, "kana"),
    KANJI(R.string.display_style_kanji, "kanji");

    private final int resId;

    private final String code;

    KarutaStyle(int resId, String code) {
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
