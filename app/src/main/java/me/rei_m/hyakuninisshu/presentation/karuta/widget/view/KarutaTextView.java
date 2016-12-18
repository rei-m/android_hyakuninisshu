package me.rei_m.hyakuninisshu.presentation.karuta.widget.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import me.rei_m.hyakuninisshu.R;

public class KarutaTextView extends TextView {

    public KarutaTextView(Context context) {
        super(context);
        initialize(context);
    }

    public KarutaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public KarutaTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        Resources resources = context.getResources();
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), resources.getString(R.string.app_font_file));
        setTypeface(typeface);
    }
}
