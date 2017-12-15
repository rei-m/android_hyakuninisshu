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

package me.rei_m.hyakuninisshu.presentation.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;
import java.util.Locale;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizJudgement;
import me.rei_m.hyakuninisshu.presentation.helper.GlideApp;
import me.rei_m.hyakuninisshu.presentation.helper.KarutaDisplayHelper;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.view.KarutaExamResultView;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.view.KarutaView;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.view.VerticalSingleLineTextView;

public class DataBindingAttributeBinder {

    private DataBindingAttributeBinder() {
    }

    @BindingAdapter({"textSizeByPx"})
    public static void setTextSizeByPx(@NonNull TextView view,
                                       int textSize) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    @BindingAdapter({"verticalText"})
    public static void setVerticalText(@NonNull VerticalSingleLineTextView view,
                                       @NonNull String text) {
        view.drawText(text);
    }

    @BindingAdapter({"verticalTextSize"})
    public static void setVerticalTextSize(@NonNull VerticalSingleLineTextView view,
                                           @DimenRes int dimenId) {
        view.setTextSize(dimenId);
    }

    @BindingAdapter({"verticalTextSizeByPx"})
    public static void setVerticalTextSizeByPx(@NonNull VerticalSingleLineTextView view,
                                               int textSize) {
        view.setTextSizeByPx(textSize);
    }

    @BindingAdapter({"examJudgements"})
    public static void setKarutaExamResult(@NonNull KarutaExamResultView view,
                                           @Nullable List<KarutaQuizJudgement> judgements) {
        if (judgements == null) {
            return;
        }
        view.setResult(judgements);
    }

    @BindingAdapter({"karuta"})
    public static void setKaruta(@NonNull KarutaView view,
                                 @Nullable Karuta karuta) {

        if (karuta == null) {
            return;
        }
        view.setKaruta(karuta);
    }

    @BindingAdapter({"karutaSrc"})
    public static void setKarutaSrc(@NonNull ImageView view,
                                    @Nullable String resIdString) {
        if (resIdString == null) {
            return;
        }

        Context context = view.getContext().getApplicationContext();

        int resId = context.getResources().getIdentifier("karuta_" + resIdString, "drawable", context.getPackageName());

        GlideApp.with(view.getContext())
                .load(resId)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view);
    }

    @BindingAdapter({"averageAnswerSec"})
    public static void setAverageAnswerTime(@NonNull TextView view,
                                            float averageAnswerSec) {
        Context context = view.getContext().getApplicationContext();
        final String averageAnswerTimeString = String.format(Locale.JAPAN, "%.2f", averageAnswerSec);
        view.setText(context.getString(R.string.seconds, averageAnswerTimeString));
    }

    @BindingAdapter({"karutaNo"})
    public static void setKarutaNo(@NonNull TextView view,
                                   int karutaNo) {
        if (karutaNo < 1) {
            return;
        }

        Context context = view.getContext().getApplicationContext();
        String text = KarutaDisplayHelper.convertNumberToString(context, karutaNo);
        view.setText(text);
    }

    @BindingAdapter({"karutaNo", "creator"})
    public static void setKarutaNoAndCreator(@NonNull TextView view,
                                             int karutaNo,
                                             @NonNull String creator) {

        Context context = view.getContext().getApplicationContext();

        String text = KarutaDisplayHelper.convertNumberToString(context, karutaNo) + " / " + creator;
        view.setText(text);
    }
}
