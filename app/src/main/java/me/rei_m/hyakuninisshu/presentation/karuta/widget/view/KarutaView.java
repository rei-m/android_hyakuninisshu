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
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.presentation.helper.GlideApp;
import me.rei_m.hyakuninisshu.presentation.helper.KarutaDisplayHelper;

import static me.rei_m.hyakuninisshu.util.Constants.SPACE;

public class KarutaView extends LinearLayout {

    private ImageView imageView;
    private TextView creatorView;
    private TextView kamiNoKuKanjiView;
    private TextView shimoNoKuKanjiView;
    private TextView kamiNoKuKanaView;
    private TextView shimoNoKuKanaView;
    private TextView translationView;

    public KarutaView(Context context) {
        super(context);
        initialize(context);
    }

    public KarutaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public KarutaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(@NonNull Context context) {
        setOrientation(VERTICAL);
        setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View.inflate(context, R.layout.view_karuta, this);
        imageView = findViewById(R.id.view_karuta_image);
        creatorView = findViewById(R.id.view_karuta_creator);
        kamiNoKuKanjiView = findViewById(R.id.view_karuta_kami_no_ku_kanji);
        shimoNoKuKanjiView = findViewById(R.id.view_karuta_shimo_no_ku_kanji);
        kamiNoKuKanaView = findViewById(R.id.view_karuta_kami_no_ku_kana);
        shimoNoKuKanaView = findViewById(R.id.view_karuta_shimo_no_ku_kana);
        translationView = findViewById(R.id.view_karuta_translation);
    }

    public void setKaruta(@NonNull Karuta karuta) {

        Context context = getContext().getApplicationContext();

        int resId = context.getResources().getIdentifier("karuta_" + karuta.imageNo().value(), "drawable", context.getPackageName());

        GlideApp.with(imageView.getContext())
                .load(resId)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

        String creatorText = KarutaDisplayHelper.convertNumberToString(context, karuta.identifier().value()) + " / " + karuta.creator();
        creatorView.setText(creatorText);
        kamiNoKuKanjiView.setText(karuta.kamiNoKu().kanji());
        shimoNoKuKanjiView.setText(karuta.shimoNoKu().kanji());
        setKamiNoKuKana(karuta.kamiNoKu().kana(), karuta.kimariji().value());
        shimoNoKuKanaView.setText(karuta.shimoNoKu().kana());
        translationView.setText(karuta.translation());
    }

    private void setKamiNoKuKana(@NonNull String kamiNoKu, int kimariji) {
        int finallyKimariji = 0;
        for (int i = 0; i < kamiNoKu.length() - 1; i++) {
            if (kamiNoKu.substring(i, i + 1).equals(SPACE)) {
                finallyKimariji++;
            } else {
                if (kimariji < i) {
                    break;
                }
            }
            finallyKimariji++;
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder().append(kamiNoKu);
        ssb.setSpan(new ForegroundColorSpan(Color.RED), 0, finallyKimariji - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        kamiNoKuKanaView.setText(ssb);
    }
}
