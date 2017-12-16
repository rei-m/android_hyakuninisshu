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

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.util.Unit;

public class MaterialEditFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final KarutaModel karutaModel;

        private final Karuta karuta;

        private String firstPhraseKanji;
        private String firstPhraseKana;
        private String secondPhraseKanji;
        private String secondPhraseKana;
        private String thirdPhraseKanji;
        private String thirdPhraseKana;
        private String fourthPhraseKanji;
        private String fourthPhraseKana;
        private String fifthPhraseKanji;
        private String fifthPhraseKana;

        public Factory(@NonNull KarutaModel karutaModel,
                       @NonNull Karuta karuta) {
            this.karutaModel = karutaModel;
            this.karuta = karuta;
            firstPhraseKanji = karuta.kamiNoKu().first().kanji();
            firstPhraseKana = karuta.kamiNoKu().first().kana();
            secondPhraseKanji = karuta.kamiNoKu().second().kanji();
            secondPhraseKana = karuta.kamiNoKu().second().kana();
            thirdPhraseKanji = karuta.kamiNoKu().third().kanji();
            thirdPhraseKana = karuta.kamiNoKu().third().kana();
            fourthPhraseKanji = karuta.shimoNoKu().fourth().kanji();
            fourthPhraseKana = karuta.shimoNoKu().fourth().kana();
            fifthPhraseKanji = karuta.shimoNoKu().fifth().kanji();
            fifthPhraseKana = karuta.shimoNoKu().fifth().kana();
        }

        public void setFirstPhraseKanji(@NonNull String firstPhraseKanji) {
            this.firstPhraseKanji = firstPhraseKanji;
        }

        public void setFirstPhraseKana(@NonNull String firstPhraseKana) {
            this.firstPhraseKana = firstPhraseKana;
        }

        public void setSecondPhraseKanji(@NonNull String secondPhraseKanji) {
            this.secondPhraseKanji = secondPhraseKanji;
        }

        public void setSecondPhraseKana(@NonNull String secondPhraseKana) {
            this.secondPhraseKana = secondPhraseKana;
        }

        public void setThirdPhraseKanji(@NonNull String thirdPhraseKanji) {
            this.thirdPhraseKanji = thirdPhraseKanji;
        }

        public void setThirdPhraseKana(@NonNull String thirdPhraseKana) {
            this.thirdPhraseKana = thirdPhraseKana;
        }

        public void setFourthPhraseKanji(@NonNull String fourthPhraseKanji) {
            this.fourthPhraseKanji = fourthPhraseKanji;
        }

        public void setFourthPhraseKana(@NonNull String fourthPhraseKana) {
            this.fourthPhraseKana = fourthPhraseKana;
        }

        public void setFifthPhraseKanji(@NonNull String fifthPhraseKanji) {
            this.fifthPhraseKanji = fifthPhraseKanji;
        }

        public void setFifthPhraseKana(@NonNull String fifthPhraseKana) {
            this.fifthPhraseKana = fifthPhraseKana;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MaterialEditFragmentViewModel.class)) {
                return (T) new MaterialEditFragmentViewModel(
                        karutaModel,
                        karuta.identifier(),
                        firstPhraseKanji,
                        firstPhraseKana,
                        secondPhraseKanji,
                        secondPhraseKana,
                        thirdPhraseKanji,
                        thirdPhraseKana,
                        fourthPhraseKanji,
                        fourthPhraseKana,
                        fifthPhraseKanji,
                        fifthPhraseKana);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableInt karutaNo = new ObservableInt();

    public final ObservableField<String> creator = new ObservableField<>();

    public final ObservableInt kimariji = new ObservableInt();

    public final ObservableField<String> firstPhraseKanji;

    public final ObservableField<String> firstPhraseKana;

    public final ObservableField<String> secondPhraseKanji;

    public final ObservableField<String> secondPhraseKana;

    public final ObservableField<String> thirdPhraseKanji;

    public final ObservableField<String> thirdPhraseKana;

    public final ObservableField<String> fourthPhraseKanji;

    public final ObservableField<String> fourthPhraseKana;

    public final ObservableField<String> fifthPhraseKanji;

    public final ObservableField<String> fifthPhraseKana;

    private final PublishSubject<Unit> onClickEditEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickEditEvent = onClickEditEventSubject;

    private final PublishSubject<Unit> onErrorEditEventSubject = PublishSubject.create();
    public final Observable<Unit> onErrorEditEvent = onErrorEditEventSubject;

    private final PublishSubject<Unit> onUpdateMaterialEventSubject = PublishSubject.create();
    public final Observable<Unit> onUpdateMaterialEvent = onUpdateMaterialEventSubject;

    private final KarutaModel karutaModel;

    private final KarutaIdentifier karutaId;

    private CompositeDisposable disposable = null;

    public MaterialEditFragmentViewModel(@NonNull KarutaModel karutaModel,
                                         @NonNull KarutaIdentifier karutaId,
                                         @NonNull String firstPhraseKanji,
                                         @NonNull String firstPhraseKana,
                                         @NonNull String secondPhraseKanji,
                                         @NonNull String secondPhraseKana,
                                         @NonNull String thirdPhraseKanji,
                                         @NonNull String thirdPhraseKana,
                                         @NonNull String fourthPhraseKanji,
                                         @NonNull String fourthPhraseKana,
                                         @NonNull String fifthPhraseKanji,
                                         @NonNull String fifthPhraseKana) {

        this.karutaModel = karutaModel;
        this.karutaId = karutaId;
        this.firstPhraseKanji = new ObservableField<>(firstPhraseKanji);
        this.firstPhraseKana = new ObservableField<>(firstPhraseKana);
        this.secondPhraseKanji = new ObservableField<>(secondPhraseKanji);
        this.secondPhraseKana = new ObservableField<>(secondPhraseKana);
        this.thirdPhraseKanji = new ObservableField<>(thirdPhraseKanji);
        this.thirdPhraseKana = new ObservableField<>(thirdPhraseKana);
        this.fourthPhraseKanji = new ObservableField<>(fourthPhraseKanji);
        this.fourthPhraseKana = new ObservableField<>(fourthPhraseKana);
        this.fifthPhraseKanji = new ObservableField<>(fifthPhraseKanji);
        this.fifthPhraseKana = new ObservableField<>(fifthPhraseKana);

        disposable = new CompositeDisposable();
        disposable.addAll(karutaModel.editedEvent.subscribe(onUpdateMaterialEventSubject::onNext));
    }

    @Override
    protected void onCleared() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onCleared();
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
            onErrorEditEventSubject.onNext(Unit.INSTANCE);
            return;
        }
        onClickEditEventSubject.onNext(Unit.INSTANCE);
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
