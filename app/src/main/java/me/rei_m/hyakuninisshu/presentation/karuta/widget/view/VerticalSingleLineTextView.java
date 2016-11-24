package me.rei_m.hyakuninisshu.presentation.karuta.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.DimenRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import me.rei_m.hyakuninisshu.R;

public class VerticalSingleLineTextView extends View {

    private Paint paint = new Paint();

    private String text;

    private int textSize;

    public VerticalSingleLineTextView(Context context) {
        super(context);
        initialize(context);
    }

    public VerticalSingleLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public VerticalSingleLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {

        text = "";
        textSize = context.getResources().getDimensionPixelOffset(R.dimen.text_l);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
    }

    public void setTextSize(@DimenRes int dimenId) {
        textSize = getContext().getResources().getDimensionPixelOffset(dimenId);
        paint.setTextSize(textSize);
    }

    public void setTextSizeByPx(int textSize) {
        paint.setTextSize(textSize);
    }

    public void drawText(String text) {
        if (text != null) {
            this.text = text;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = textSize;
        layoutParams.height = (int) (textSize * this.text.length() * 1.05);
        setLayoutParams(layoutParams);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int textLength = text.length();
        for (int i = 0; i < textLength; i++) {
            canvas.drawText(text.substring(i, i + 1), 0, textSize * (i + 1), paint);
        }
    }
}
