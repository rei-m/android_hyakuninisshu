package me.rei_m.hyakuninisshu.presentation.utilitty;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.QuizState;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.view.KarutaExamResultView;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.view.VerticalSingleLineTextView;

import static me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaConstant.SPACE;

public class DataBindingAttributeBinder {

    private DataBindingAttributeBinder() {
    }

    @BindingAdapter({"textForQuiz", "textPosition"})
    public static void setTextForQuiz(@NonNull TextView view,
                                      @Nullable String text,
                                      int textPosition) {
        if (text == null || text.length() < textPosition) {
            return;
        }
        view.setText(text.substring(textPosition - 1, textPosition));
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

    @BindingAdapter({"stringDrawableId"})
    public static void setStringDrawableId(@NonNull ImageView view,
                                           @Nullable String drawableId) {
        if (drawableId == null) {
            return;
        }
        int resId = view.getResources().getIdentifier(drawableId, "drawable", view.getContext().getApplicationContext().getPackageName());
        Glide.with(view.getContext()).load(resId).into(view);
    }

    @BindingAdapter({"visibilityByQuizState"})
    public static void setVisibilityByQuizState(@NonNull ImageView imageView,
                                                @Nullable ObservableField<QuizState> quizState) {
        if (quizState == null) {
            imageView.setVisibility(View.GONE);
            return;
        }

        switch (quizState.get()) {
            case UNANSWERED:
                imageView.setVisibility(View.GONE);
                break;
            case ANSWERED_COLLECT:
                Glide.with(imageView.getContext()).load(R.drawable.check_correct).dontAnimate().into(imageView);
                imageView.setVisibility(View.VISIBLE);
                break;
            case ANSWERED_INCORRECT:
                Glide.with(imageView.getContext()).load(R.drawable.check_incorrect).dontAnimate().into(imageView);
                imageView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @BindingAdapter({"examResultList"})
    public static void setKarutaExamResult(@NonNull KarutaExamResultView view,
                                           @Nullable boolean[] karutaQuizResultList) {
        if (karutaQuizResultList == null) {
            return;
        }
        view.setResult(karutaQuizResultList);
    }

    @BindingAdapter({"elevation"})
    public static void setElevation(@NonNull View view,
                                    @DimenRes int elevation) {
        ViewCompat.setElevation(view, view.getContext().getResources().getDimension(elevation));
    }

    @BindingAdapter({"karutaSrc"})
    public static void setKarutaSrc(@NonNull ImageView view,
                                    @Nullable String resIdString) {
        if (resIdString == null) {
            return;
        }

        Context context = view.getContext().getApplicationContext();

        int resId = context.getResources().getIdentifier("karuta_" + resIdString, "drawable", context.getPackageName());

        Glide.with(view.getContext())
                .load(resId)
                .crossFade()
                .into(view);
    }

    @BindingAdapter({"textTopPhraseKana", "kimariji"})
    public static void setTextTopPhraseKana(@NonNull TextView view,
                                            @Nullable String topPhrase,
                                            int kimariji) {
        if (topPhrase == null) {
            return;
        }

        int finallyKimariji = 0;
        for (int i = 0; i < topPhrase.length() - 1; i++) {
            if (!topPhrase.substring(i, i + 1).equals(SPACE) && kimariji < i) {
                break;
            }
            finallyKimariji++;
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder().append(topPhrase);
        ssb.setSpan(new ForegroundColorSpan(Color.RED), 0, finallyKimariji - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(ssb);
    }
}
