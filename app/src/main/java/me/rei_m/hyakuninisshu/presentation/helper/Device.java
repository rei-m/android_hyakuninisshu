package me.rei_m.hyakuninisshu.presentation.helper;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.module.ForApplication;

@ForApplication
public class Device {

    private final Context context;

    @Inject
    public Device(@ForApplication Context context) {
        this.context = context;
    }

    public Point getWindowSize() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point;
    }
}
