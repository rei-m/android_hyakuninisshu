package me.rei_m.hyakuninisshu.presentation.karuta.widget.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaConstant;
import me.rei_m.hyakuninisshu.presentation.utility.ViewUtil;

public class KarutaExamResultView extends LinearLayout {

    private static final int NUMBER_OF_KARUTA_PER_ROW = 5;

    private static final int NUMBER_OF_KARUTA_ROW = KarutaConstant.NUMBER_OF_KARUTA / NUMBER_OF_KARUTA_PER_ROW;

    private static final int[] cellViewIdList = new int[KarutaConstant.NUMBER_OF_KARUTA];

    static {
        for (int i = 0; i < KarutaConstant.NUMBER_OF_KARUTA; i++) {
            int viewId;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                viewId = View.generateViewId();
            } else {
                viewId = ViewUtil.generateViewId();
            }
            cellViewIdList[i] = viewId;
        }
    }

    private PublishSubject<Integer> onClickKarutaEventSubject = PublishSubject.create();
    public Observable<Integer> onClickKarutaEvent = onClickKarutaEventSubject;

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
        for (int i = 0; i < NUMBER_OF_KARUTA_ROW; i++) {
            addView(createRow(context, i));
        }
    }

    private LinearLayout createRow(Context context, int rowIndex) {

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(HORIZONTAL);

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);

        final int currentRowTopIndex = (rowIndex * NUMBER_OF_KARUTA_PER_ROW);

        for (int i = 0; i < NUMBER_OF_KARUTA_PER_ROW; i++) {
            int totalIndex = currentRowTopIndex + i;
            KarutaExamResultCellView cellView = new KarutaExamResultCellView(context);
            //noinspection ResourceType
            cellView.setId(cellViewIdList[totalIndex]);
            cellView.setGravity(Gravity.CENTER);
            linearLayout.addView(cellView, layoutParams);
        }

        return linearLayout;
    }

    public void setResult(boolean[] karutaQuizResultList) {
        for (int i = 0; i < karutaQuizResultList.length; i++) {
            KarutaExamResultCellView cellView = (KarutaExamResultCellView) findViewById(cellViewIdList[i]);
            int karutaNo = i + 1;
            cellView.setResult(karutaNo, karutaQuizResultList[i]);
            cellView.setOnClickListener(view -> onClickKarutaEventSubject.onNext(karutaNo));
        }
    }
}
