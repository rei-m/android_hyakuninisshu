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

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.util.EventObservable;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class MaterialEditFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableInt karutaNo = new ObservableInt();

    public final ObservableField<String> creator = new ObservableField<>();

    public final ObservableInt kimariji = new ObservableInt();

    public final ObservableField<String> firstPhraseKanji = new ObservableField<>();

    public final ObservableField<String> firstPhraseKana = new ObservableField<>();

    public final ObservableField<String> secondPhraseKanji = new ObservableField<>();

    public final ObservableField<String> secondPhraseKana = new ObservableField<>();

    public final ObservableField<String> thirdPhraseKanji = new ObservableField<>();

    public final ObservableField<String> thirdPhraseKana = new ObservableField<>();

    public final ObservableField<String> fourthPhraseKanji = new ObservableField<>();

    public final ObservableField<String> fourthPhraseKana = new ObservableField<>();

    public final ObservableField<String> fifthPhraseKanji = new ObservableField<>();

    public final ObservableField<String> fifthPhraseKana = new ObservableField<>();

    public final EventObservable<Unit> onClickEditEvent = EventObservable.create();

    public final EventObservable<Unit> onErrorEditEvent = EventObservable.create();

    public final EventObservable<Unit> onUpdateMaterialEvent = EventObservable.create();

    private final KarutaModel karutaModel;

    private KarutaIdentifier karutaId;

    public MaterialEditFragmentViewModel(@NonNull KarutaModel karutaModel) {
        this.karutaModel = karutaModel;
    }

    public void onCreate(@NonNull KarutaIdentifier karutaId) {
        this.karutaId = karutaId;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaModel.completeFetchKarutaEvent.subscribe(karuta -> {

            if (!karutaId.equals(karuta.identifier())) {
                return;
            }

            karutaNo.set(karuta.identifier().value());
            creator.set(karuta.creator());
            kimariji.set(karuta.kimariji().value());
            firstPhraseKanji.set(karuta.kamiNoKu().first().kanji());
            firstPhraseKana.set(karuta.kamiNoKu().first().kana());
            secondPhraseKanji.set(karuta.kamiNoKu().second().kanji());
            secondPhraseKana.set(karuta.kamiNoKu().second().kana());
            thirdPhraseKanji.set(karuta.kamiNoKu().third().kanji());
            thirdPhraseKana.set(karuta.kamiNoKu().third().kana());
            fourthPhraseKanji.set(karuta.shimoNoKu().fourth().kanji());
            fourthPhraseKana.set(karuta.shimoNoKu().fourth().kana());
            fifthPhraseKanji.set(karuta.shimoNoKu().fifth().kanji());
            fifthPhraseKana.set(karuta.shimoNoKu().fifth().kana());
        }), karutaModel.completeEditKarutaEvent.subscribe(v -> onUpdateMaterialEvent.onNext(Unit.INSTANCE)));
    }

    @Override
    public void onResume() {
        super.onResume();
        karutaModel.fetchKaruta(karutaId);
    }

    @SuppressWarnings("unused")
    public void onClickEdit(View view) {
        if (firstPhraseKanji.get().isEmpty() ||
                firstPhraseKana.get().isEmpty() ||
                secondPhraseKanji.get().isEmpty() ||
                secondPhraseKana.get().isEmpty() ||
                thirdPhraseKanji.get().isEmpty() ||
                thirdPhraseKana.get().isEmpty() ||
                fourthPhraseKanji.get().isEmpty() ||
                fourthPhraseKana.get().isEmpty() ||
                fifthPhraseKanji.get().isEmpty() ||
                firstPhraseKana.get().isEmpty()) {
            onErrorEditEvent.onNext(Unit.INSTANCE);
            return;
        }
        onClickEditEvent.onNext(Unit.INSTANCE);
    }

    public void onClickDialogPositive() {
        karutaModel.editKaruta(karutaId,
                firstPhraseKanji.get(),
                firstPhraseKana.get(),
                secondPhraseKanji.get(),
                secondPhraseKana.get(),
                thirdPhraseKanji.get(),
                thirdPhraseKana.get(),
                fourthPhraseKanji.get(),
                fourthPhraseKana.get(),
                fifthPhraseKanji.get(),
                fifthPhraseKana.get());
    }
}
