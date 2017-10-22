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
import android.support.annotation.NonNull;
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

    public KarutaExamResultCellView(@NonNull Context context) {
        super(context);
        initialize(context);
    }

    public KarutaExamResultCellView(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public KarutaExamResultCellView(@NonNull Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(@NonNull Context context) {
        View view = View.inflate(context, R.layout.view_karuta_exam_result_cell, this);
        textKarutaNo = view.findViewById(R.id.text_karuta_no);
        imageCorrect = view.findViewById(R.id.image_quiz_result_correct);
        imageIncorrect = view.findViewById(R.id.image_quiz_result_incorrect);
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
