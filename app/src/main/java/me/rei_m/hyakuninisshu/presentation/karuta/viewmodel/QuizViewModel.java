package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.presentation.utilitty.KarutaDisplayUtil;

public class QuizViewModel {

    public static class QuizChoiceViewModel {
        public final long id;

        public final String fourthPhrase;

        public final String fifthPhrase;

        public QuizChoiceViewModel(long id,
                                   @NonNull String fourthPhrase,
                                   @NonNull String fifthPhrase) {
            this.id = id;
            this.fourthPhrase = KarutaDisplayUtil.padSpace(fourthPhrase, 7);
            this.fifthPhrase = fifthPhrase;
        }
    }

    @BindingAdapter({"relativeLeftMarginTop"})
    public static void setRelativeLeftMarginTop(View view, int intentLevel) {

        Context context = view.getContext();

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int levelMargin = context.getResources().getDimensionPixelOffset(R.dimen.margin_karuta_phrase);
        int margin = levelMargin * intentLevel;

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) lp;
        mlp.setMargins(mlp.leftMargin + margin, mlp.topMargin, mlp.rightMargin, mlp.bottomMargin);
        view.setLayoutParams(mlp);
    }

    @BindingAdapter({"relativeLeftMarginBottom"})
    public static void setRelativeLeftMarginBottom(View view, int intentLevel) {

        Context context = view.getContext();

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int levelMargin = context.getResources().getDimensionPixelOffset(R.dimen.margin_karuta_phrase) / 2;
        int margin = levelMargin * intentLevel;

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) lp;
        mlp.setMargins(mlp.leftMargin + margin, mlp.topMargin, mlp.rightMargin, mlp.bottomMargin);
        view.setLayoutParams(mlp);
    }

    public final String quizId;

    public final String firstPhrase;

    public final String secondPhrase;

    public final String thirdPhrase;

    public final QuizChoiceViewModel choiceFirst;

    public final QuizChoiceViewModel choiceSecond;

    public final QuizChoiceViewModel choiceThird;

    public final QuizChoiceViewModel choiceFourth;

    public QuizViewModel(String quizId,
                         String firstPhrase,
                         String secondPhrase,
                         String thirdPhrase,
                         QuizChoiceViewModel choiceFirst,
                         QuizChoiceViewModel choiceSecond,
                         QuizChoiceViewModel choiceThird,
                         QuizChoiceViewModel choiceFourth) {

        this.quizId = quizId;
        this.firstPhrase = KarutaDisplayUtil.padSpace(firstPhrase, 5);
        this.secondPhrase = secondPhrase;
        this.thirdPhrase = thirdPhrase;

        this.choiceFirst = choiceFirst;
        this.choiceSecond = choiceSecond;
        this.choiceThird = choiceThird;
        this.choiceFourth = choiceFourth;
    }
}
