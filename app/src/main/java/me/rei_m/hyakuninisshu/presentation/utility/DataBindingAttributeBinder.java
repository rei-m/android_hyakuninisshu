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

package me.rei_m.hyakuninisshu.presentation.utility;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.ads.AdView;

import java.util.Locale;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.presentation.helper.KarutaDisplayHelper;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.view.KarutaExamResultView;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.view.VerticalSingleLineTextView;
import me.rei_m.hyakuninisshu.util.GlideApp;

import static me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaConstant.SPACE;

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

    @BindingAdapter({"examResultList"})
    public static void setKarutaExamResult(@NonNull KarutaExamResultView view,
                                           @Nullable boolean[] karutaQuizResultList) {
        if (karutaQuizResultList == null) {
            return;
        }
        view.setResult(karutaQuizResultList);
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

    @BindingAdapter({"textTopPhraseKana", "kimariji"})
    public static void setTextTopPhraseKana(@NonNull TextView view,
                                            @Nullable String topPhrase,
                                            int kimariji) {
        if (topPhrase == null || kimariji < 1) {
            return;
        }

        int finallyKimariji = 0;
        for (int i = 0; i < topPhrase.length() - 1; i++) {
            if (topPhrase.substring(i, i + 1).equals(SPACE)) {
                finallyKimariji++;
            } else {
                if (kimariji < i) {
                    break;
                }
            }
            finallyKimariji++;
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder().append(topPhrase);
        ssb.setSpan(new ForegroundColorSpan(Color.RED), 0, finallyKimariji - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(ssb);
    }

    @BindingAdapter({"averageAnswerTime"})
    public static void setAverageAnswerTime(@NonNull TextView view,
                                            float averageAnswerTime) {
        Context context = view.getContext().getApplicationContext();
        final String averageAnswerTimeString = String.format(Locale.JAPAN, "%.2f", averageAnswerTime);
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

    @BindingAdapter({"visibilityWithAd"})
    public static void setVisibilityWithAd(@NonNull AdView view,
                                           boolean isVisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
            ViewUtil.loadAd(view);
        } else {
            view.destroy();
            view.setVisibility(View.GONE);
        }
    }
}
