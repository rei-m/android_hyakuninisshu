/*
 * Copyright (c) 2017. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package me.rei_m.hyakuninisshu.presentation.karuta.widget.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.event.EventObservable;
import me.rei_m.hyakuninisshu.presentation.util.ViewUtil;

public class KarutaExamResultView extends LinearLayout {

    private static final int NUMBER_OF_KARUTA_PER_ROW = 5;

    private static final int NUMBER_OF_KARUTA_ROW = Karuta.NUMBER_OF_KARUTA / NUMBER_OF_KARUTA_PER_ROW;

    private static final int[] cellViewIdList = new int[Karuta.NUMBER_OF_KARUTA];

    static {
        for (int i = 0; i < Karuta.NUMBER_OF_KARUTA; i++) {
            int viewId;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                viewId = View.generateViewId();
            } else {
                viewId = ViewUtil.generateViewId();
            }
            cellViewIdList[i] = viewId;
        }
    }

    public EventObservable<KarutaIdentifier> onClickKarutaEvent = EventObservable.create();

    public KarutaExamResultView(@NonNull Context context) {
        super(context);
        initialize(context);
    }

    public KarutaExamResultView(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public KarutaExamResultView(@NonNull Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(@NonNull Context context) {
        setOrientation(VERTICAL);
        for (int i = 0; i < NUMBER_OF_KARUTA_ROW; i++) {
            addView(createRow(context, i));
        }
    }

    private LinearLayout createRow(@NonNull Context context, int rowIndex) {

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

    public void setResult(@NonNull boolean[] karutaQuizResultList) {
        for (int i = 0; i < karutaQuizResultList.length; i++) {
            KarutaExamResultCellView cellView = findViewById(cellViewIdList[i]);
            int karutaNo = i + 1;
            cellView.setResult(karutaNo, karutaQuizResultList[i]);
            cellView.setOnClickListener(view -> onClickKarutaEvent.onNext(new KarutaIdentifier(karutaNo)));
        }
    }
}
