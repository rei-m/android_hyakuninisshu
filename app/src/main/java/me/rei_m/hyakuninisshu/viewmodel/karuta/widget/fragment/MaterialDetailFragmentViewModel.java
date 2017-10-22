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

package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.widget.TextView;

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.helper.KarutaDisplayHelper;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

import static me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaConstant.SPACE;

public class MaterialDetailFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableInt karutaNo = new ObservableInt();

    public final ObservableField<String> karutaImageNo = new ObservableField<>();

    public final ObservableField<String> creator = new ObservableField<>();

    public final ObservableInt kimariji = new ObservableInt();

    public final ObservableField<String> topPhraseKanji = new ObservableField<>();

    public final ObservableField<String> bottomPhraseKanji = new ObservableField<>();

    public final ObservableField<String> topPhraseKana = new ObservableField<>();

    public final ObservableField<String> bottomPhraseKana = new ObservableField<>();

    public final ObservableField<String> translation = new ObservableField<>();

    private final KarutaModel karutaModel;

    private KarutaIdentifier karutaIdentifier;

    public MaterialDetailFragmentViewModel(KarutaModel karutaModel) {
        this.karutaModel = karutaModel;
    }

    public void onCreate(int karutaNo) {
        karutaIdentifier = new KarutaIdentifier(karutaNo);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaModel.completeGetKarutaEvent.subscribe(karuta -> {

            if (!karutaIdentifier.equals(karuta.identifier())) {
                return;
            }

            karutaNo.set((int) karutaIdentifier.value());

            karutaImageNo.set(karuta.imageNo().value());

            creator.set(karuta.creator());

            kimariji.set(karuta.kimariji().value());

            topPhraseKanji.set(karuta.kamiNoKu().first().kanji() + SPACE +
                    karuta.kamiNoKu().second().kanji() + SPACE +
                    karuta.kamiNoKu().third().kanji());


            bottomPhraseKanji.set(karuta.shimoNoKu().fourth().kanji() + SPACE +
                    karuta.shimoNoKu().fifth().kanji());

            topPhraseKana.set(karuta.kamiNoKu().first().kana() + SPACE +
                    karuta.kamiNoKu().second().kana() + SPACE +
                    karuta.kamiNoKu().third().kana());

            bottomPhraseKana.set(karuta.shimoNoKu().fourth().kana() + SPACE +
                    karuta.shimoNoKu().fifth().kana());

            translation.set(karuta.translation());
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        karutaModel.getKaruta(karutaIdentifier);
    }

    @BindingAdapter({"karutaNo", "creator"})
    public static void setKarutaNoAndCreator(@NonNull TextView view,
                                             int karutaNo,
                                             @NonNull String creator) {

        Context context = view.getContext().getApplicationContext();

        String text = KarutaDisplayHelper.convertNumberToString(context, karutaNo) + " / " + creator;
        view.setText(text);
    }
}
