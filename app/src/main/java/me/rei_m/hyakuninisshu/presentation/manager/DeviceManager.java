package me.rei_m.hyakuninisshu.presentation.manager;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.rei_m.hyakuninisshu.module.ForApplication;

@Singleton
public class DeviceManager {

    private final Context context;

    @Inject
    public DeviceManager(@ForApplication Context context) {
        this.context = context;
    }

    public Point getWindowSize() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point;
    }
}
