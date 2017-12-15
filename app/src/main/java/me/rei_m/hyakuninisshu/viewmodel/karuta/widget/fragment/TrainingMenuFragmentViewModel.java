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
import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeTo;
import me.rei_m.hyakuninisshu.util.Unit;

public class TrainingMenuFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private TrainingRangeFrom trainingRangeFrom = TrainingRangeFrom.ONE;
        private TrainingRangeTo trainingRangeTo = TrainingRangeTo.ONE_HUNDRED;
        private KimarijiFilter kimariji = KimarijiFilter.ALL;
        private KarutaStyleFilter kamiNoKuStyle = KarutaStyleFilter.KANJI;
        private KarutaStyleFilter shimoNoKuStyle = KarutaStyleFilter.KANA;
        private ColorFilter color = ColorFilter.ALL;

        public void setTrainingRangeFrom(@NonNull TrainingRangeFrom trainingRangeFrom) {
            this.trainingRangeFrom = trainingRangeFrom;
        }

        public void setTrainingRangeTo(@NonNull TrainingRangeTo trainingRangeTo) {
            this.trainingRangeTo = trainingRangeTo;
        }

        public void setKimariji(@NonNull KimarijiFilter kimariji) {
            this.kimariji = kimariji;
        }

        public void setKamiNoKuStyle(@NonNull KarutaStyleFilter kamiNoKuStyle) {
            this.kamiNoKuStyle = kamiNoKuStyle;
        }

        public void setShimoNoKuStyle(@NonNull KarutaStyleFilter shimoNoKuStyle) {
            this.shimoNoKuStyle = shimoNoKuStyle;
        }

        public void setColor(@NonNull ColorFilter color) {
            this.color = color;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(TrainingMenuFragmentViewModel.class)) {
                return (T) new TrainingMenuFragmentViewModel(
                        trainingRangeFrom,
                        trainingRangeTo,
                        kimariji,
                        kamiNoKuStyle,
                        shimoNoKuStyle,
                        color);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableField<TrainingRangeFrom> trainingRangeFrom;

    public final ObservableField<TrainingRangeTo> trainingRangeTo;

    public final ObservableField<KimarijiFilter> kimariji;

    public final ObservableField<KarutaStyleFilter> kamiNoKuStyle;

    public final ObservableField<KarutaStyleFilter> shimoNoKuStyle;

    public final ObservableField<ColorFilter> color;

    private final PublishSubject<Unit> onClickStartTrainingEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickStartTrainingEvent = onClickStartTrainingEventSubject;

    private final PublishSubject<Unit> invalidTrainingRangeEventSubject = PublishSubject.create();
    public final Observable<Unit> invalidTrainingRangeEvent = invalidTrainingRangeEventSubject;

    public TrainingMenuFragmentViewModel(@NonNull TrainingRangeFrom trainingRangeFrom,
                                         @NonNull TrainingRangeTo trainingRangeTo,
                                         @NonNull KimarijiFilter kimariji,
                                         @NonNull KarutaStyleFilter kamiNoKuStyle,
                                         @NonNull KarutaStyleFilter shimoNoKuStyle,
                                         @NonNull ColorFilter color) {
        this.trainingRangeFrom = new ObservableField<>(trainingRangeFrom);
        this.trainingRangeTo = new ObservableField<>(trainingRangeTo);
        this.kimariji = new ObservableField<>(kimariji);
        this.kamiNoKuStyle = new ObservableField<>(kamiNoKuStyle);
        this.shimoNoKuStyle = new ObservableField<>(shimoNoKuStyle);
        this.color = new ObservableField<>(color);
    }

    @SuppressWarnings("unused")
    public void onClickStartTraining(View view) {

        if (trainingRangeFrom.get().ordinal() > trainingRangeTo.get().ordinal()) {
            invalidTrainingRangeEventSubject.onNext(Unit.INSTANCE);
            return;
        }

        onClickStartTrainingEventSubject.onNext(Unit.INSTANCE);
    }
}
