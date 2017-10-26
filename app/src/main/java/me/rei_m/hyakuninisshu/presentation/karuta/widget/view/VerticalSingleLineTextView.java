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

package me.rei_m.hyakuninisshu.presentation.karuta.widget.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import me.rei_m.hyakuninisshu.R;

public class VerticalSingleLineTextView extends View {

    private final Paint paint = new Paint();

    private String text;

    private int textSize;

    public VerticalSingleLineTextView(@NonNull Context context) {
        super(context);
        initialize(context);
    }

    public VerticalSingleLineTextView(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public VerticalSingleLineTextView(@NonNull Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(@NonNull Context context) {
        Resources resources = context.getResources();
        text = "";
        textSize = context.getResources().getDimensionPixelOffset(R.dimen.text_l);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setColor(ResourcesCompat.getColor(resources, R.color.black8a, null));
        paint.setTypeface(KarutaFontHolder.INSTANCE.getTypeFace(context));
    }

    public void setTextSize(@DimenRes int dimenId) {
        textSize = getContext().getResources().getDimensionPixelOffset(dimenId);
        paint.setTextSize(textSize);
    }

    public void setTextSizeByPx(int textSize) {
        this.textSize = textSize;
        paint.setTextSize(textSize);
    }

    public void drawText(@Nullable String text) {
        if (text != null) {
            this.text = text;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = textSize;
        layoutParams.height = (int) (textSize * this.text.length() * 1.05);
        setLayoutParams(layoutParams);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int textLength = text.length();
        for (int i = 0; i < textLength; i++) {
            canvas.drawText(text.substring(i, i + 1), 0, textSize * (i + 1), paint);
        }
    }
}
