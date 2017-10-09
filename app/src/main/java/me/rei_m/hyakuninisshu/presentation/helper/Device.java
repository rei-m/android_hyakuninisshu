package me.rei_m.hyakuninisshu.presentation.helper;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Device {

    private final Context context;

    @Inject
    public Device(@NonNull Context context) {
        this.context = context;
    }

    public Point getWindowSize() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point;
    }
}
