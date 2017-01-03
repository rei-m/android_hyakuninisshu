package me.rei_m.hyakuninisshu.presentation.karuta.widget.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.R;

enum KarutaFontHolder {
    INSTANCE;

    private Typeface typeface;

    public Typeface getTypeFace(@NonNull Context context) {
        if (typeface == null) {
            Resources resources = context.getResources();
            typeface = Typeface.createFromAsset(context.getAssets(), resources.getString(R.string.app_font_file));
        }
        return typeface;
    }
}
