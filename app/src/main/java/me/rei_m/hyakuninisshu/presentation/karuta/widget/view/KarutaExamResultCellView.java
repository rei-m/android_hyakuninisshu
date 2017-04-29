package me.rei_m.hyakuninisshu.presentation.karuta.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.presentation.helper.KarutaDisplayHelper;

public class KarutaExamResultCellView extends RelativeLayout {

    private TextView textKarutaNo;

    private ImageView imageCorrect;

    private ImageView imageIncorrect;

    public KarutaExamResultCellView(Context context) {
        super(context);
        initialize(context);
    }

    public KarutaExamResultCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public KarutaExamResultCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        View view = View.inflate(context, R.layout.view_karuta_exam_result_cell, this);
        textKarutaNo = (TextView) view.findViewById(R.id.text_karuta_no);
        imageCorrect = (ImageView) view.findViewById(R.id.image_quiz_result_correct);
        imageIncorrect = (ImageView) view.findViewById(R.id.image_quiz_result_incorrect);
    }

    public void setResult(int karutaNo, boolean isCorrect) {
        textKarutaNo.setText(KarutaDisplayHelper.convertNumberToString(getContext(), karutaNo));
        if (isCorrect) {
            imageCorrect.setVisibility(VISIBLE);
        } else {
            imageIncorrect.setVisibility(VISIBLE);
        }
    }
}
