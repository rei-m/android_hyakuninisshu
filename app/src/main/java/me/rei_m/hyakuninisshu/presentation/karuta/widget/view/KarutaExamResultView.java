package me.rei_m.hyakuninisshu.presentation.karuta.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class KarutaExamResultView extends LinearLayout {

    public KarutaExamResultView(Context context) {
        super(context);
        initialize(context);
    }

    public KarutaExamResultView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public KarutaExamResultView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        setOrientation(VERTICAL);
        for (int i = 0; i < 20; i++) {
            addView(createRow(context));
        }
    }

    private LinearLayout createRow(Context context) {

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(HORIZONTAL);

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        
        for (int i = 0; i < 5; i++) {
            KarutaExamResultCellView cellView = new KarutaExamResultCellView(context);
            cellView.setGravity(Gravity.CENTER);
            linearLayout.addView(cellView, layoutParams);
        }

        return linearLayout;
    }
}
