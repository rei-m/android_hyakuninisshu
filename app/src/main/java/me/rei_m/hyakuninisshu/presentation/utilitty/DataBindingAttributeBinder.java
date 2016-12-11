package me.rei_m.hyakuninisshu.presentation.utilitty;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.QuizState;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.view.KarutaExamResultView;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.view.VerticalSingleLineTextView;

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
        view.setImageResource(resId);
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
                imageView.setImageResource(R.drawable.check_correct);
                imageView.setVisibility(View.VISIBLE);
                break;
            case ANSWERED_INCORRECT:
                imageView.setImageResource(R.drawable.check_incorrect);
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
}
