package me.rei_m.hyakuninisshu.presentation.karuta.widget.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TextView;

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

    private void initialize(@NonNull Context context) {
        setTypeface(KarutaFontHolder.INSTANCE.getTypeFace(context));
    }
}
