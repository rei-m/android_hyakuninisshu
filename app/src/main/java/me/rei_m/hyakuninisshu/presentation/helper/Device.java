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
