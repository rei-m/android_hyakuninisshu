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

package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.adapter;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;

import static me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaConstant.SPACE;

public class KarutaListItemViewModel {

    public final ObservableInt karutaNo = new ObservableInt(0);

    public final ObservableField<String> karutaImageNo = new ObservableField<>("");

    public final ObservableField<String> creator = new ObservableField<>("");

    public final ObservableField<String> topPhrase = new ObservableField<>("");

    public final ObservableField<String> bottomPhrase = new ObservableField<>("");

    private final Navigator navigator;

    @Inject
    public KarutaListItemViewModel(@NonNull Navigator navigator) {
        this.navigator = navigator;
    }

    public void setKaruta(@NonNull Karuta karuta) {
        String topPhrase = karuta.kamiNoKu().first().kanji() + SPACE +
                karuta.kamiNoKu().second().kanji() + SPACE +
                karuta.kamiNoKu().third().kanji();

        String bottomPhrase = karuta.shimoNoKu().fourth().kanji() + SPACE +
                karuta.shimoNoKu().fifth().kanji();

        int karutaNo = (int) karuta.identifier().value();

        this.karutaNo.set(karutaNo);
        this.karutaImageNo.set(karuta.imageNo().value());
        this.creator.set(karuta.creator());
        this.topPhrase.set(topPhrase);
        this.bottomPhrase.set(bottomPhrase);
    }

    @SuppressWarnings("unused")
    public void onItemClicked(View view) {
        navigator.navigateToMaterialDetail(karutaNo.get());
    }
}
