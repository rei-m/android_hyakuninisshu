package me.rei_m.hyakuninisshu.presentation.utilitty;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Point;
import android.support.annotation.DimenRes;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.presentation.karuta.component.view.VerticalSingleLineTextView;

public class DataBindingAttributeBinder {

    private DataBindingAttributeBinder() {
    }

    @BindingAdapter({"verticalText"})
    public static void setVerticalText(VerticalSingleLineTextView view, String text) {
        view.drawText(text);
    }

    @BindingAdapter({"verticalTextSize"})
    public static void setVerticalTextSize(VerticalSingleLineTextView view, @DimenRes int dimenId) {
        view.setTextSize(dimenId);
    }

    @BindingAdapter({"stringDrawableId"})
    public static void setStringDrawableId(ImageView view, String drawableId) {
        if (drawableId == null) {
            return;
        }
        int resId = view.getResources().getIdentifier(drawableId, "drawable", view.getContext().getApplicationContext().getPackageName());
        view.setImageResource(resId);
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
}
