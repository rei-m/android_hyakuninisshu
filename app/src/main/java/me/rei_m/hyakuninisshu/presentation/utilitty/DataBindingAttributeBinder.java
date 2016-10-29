package me.rei_m.hyakuninisshu.presentation.utilitty;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import me.rei_m.hyakuninisshu.R;

public class DataBindingAttributeBinder {

    private DataBindingAttributeBinder() {
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
