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
import android.support.annotation.Nullable;
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

        private final KarutaIdentifier karutaId;

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
                       @NonNull KarutaIdentifier karutaId) {
            this.karutaModel = karutaModel;
            this.karutaId = karutaId;
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
                        karutaId,
                        firstPhraseKanji,
                        firstPhraseKana,
                        secondPhraseKanji,
                        secondPhraseKana,
                        thirdPhraseKanji,
                        thirdPhraseKana,
                        fourthPhraseKanji,
                        fourthPhraseKana,
                        fifthPhraseKanji,
                        fifthPhraseKana
                );
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

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

    private final PublishSubject<Unit> onClickEditEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickEditEvent = onClickEditEventSubject;

    private final PublishSubject<Unit> onErrorEditEventSubject = PublishSubject.create();
    public final Observable<Unit> onErrorEditEvent = onErrorEditEventSubject;

    private final PublishSubject<Unit> onUpdateMaterialEventSubject = PublishSubject.create();
    public final Observable<Unit> onUpdateMaterialEvent = onUpdateMaterialEventSubject;

    private final KarutaModel karutaModel;

    private final KarutaIdentifier karutaId;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public MaterialEditFragmentViewModel(@NonNull KarutaModel karutaModel,
                                         @NonNull KarutaIdentifier karutaId,
                                         @Nullable String firstPhraseKanji,
                                         @Nullable String firstPhraseKana,
                                         @Nullable String secondPhraseKanji,
                                         @Nullable String secondPhraseKana,
                                         @Nullable String thirdPhraseKanji,
                                         @Nullable String thirdPhraseKana,
                                         @Nullable String fourthPhraseKanji,
                                         @Nullable String fourthPhraseKana,
                                         @Nullable String fifthPhraseKanji,
                                         @Nullable String fifthPhraseKana) {

        this.karutaModel = karutaModel;
        this.karutaId = karutaId;
        disposable.addAll(karutaModel.karutas.subscribe(karutas -> {
            Karuta karuta = karutas.get(karutaId);
            this.karutaNo.set(karuta.identifier().value());
            this.creator.set(karuta.creator());
            this.kimariji.set(karuta.kimariji().value());
            if (firstPhraseKanji == null) {
                this.firstPhraseKanji.set(karuta.kamiNoKu().first().kanji());
            } else {
                this.firstPhraseKanji.set(firstPhraseKanji);
            }
            if (firstPhraseKana == null) {
                this.firstPhraseKana.set(karuta.kamiNoKu().first().kana());
            } else {
                this.firstPhraseKana.set(firstPhraseKana);
            }
            if (secondPhraseKanji == null) {
                this.secondPhraseKanji.set(karuta.kamiNoKu().second().kanji());
            } else {
                this.secondPhraseKanji.set(secondPhraseKanji);
            }
            if (secondPhraseKana == null) {
                this.secondPhraseKana.set(karuta.kamiNoKu().second().kana());
            } else {
                this.secondPhraseKana.set(secondPhraseKana);
            }
            if (thirdPhraseKanji == null) {
                this.thirdPhraseKanji.set(karuta.kamiNoKu().third().kanji());
            } else {
                this.thirdPhraseKanji.set(thirdPhraseKanji);
            }
            if (thirdPhraseKana == null) {
                this.thirdPhraseKana.set(karuta.kamiNoKu().third().kana());
            } else {
                this.thirdPhraseKana.set(thirdPhraseKana);
            }
            if (fourthPhraseKanji == null) {
                this.fourthPhraseKanji.set(karuta.shimoNoKu().fourth().kanji());
            } else {
                this.fourthPhraseKanji.set(fourthPhraseKanji);
            }
            if (fourthPhraseKana == null) {
                this.fourthPhraseKana.set(karuta.shimoNoKu().fourth().kana());
            } else {
                this.fourthPhraseKana.set(fourthPhraseKana);
            }
            if (fifthPhraseKanji == null) {
                this.fifthPhraseKanji.set(karuta.shimoNoKu().fifth().kanji());
            } else {
                this.fifthPhraseKanji.set(fifthPhraseKanji);
            }
            if (fifthPhraseKana == null) {
                this.fifthPhraseKana.set(karuta.shimoNoKu().fifth().kana());
            } else {
                this.fifthPhraseKana.set(fifthPhraseKana);
            }
        }), karutaModel.editedEvent.subscribe(onUpdateMaterialEventSubject::onNext));
        karutaModel.fetchKarutas();
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
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
