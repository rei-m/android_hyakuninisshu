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

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public TrainingMenuFragmentViewModel create(@NonNull Class modelClass) {
            return new TrainingMenuFragmentViewModel();
        }
    }

    public final ObservableField<TrainingRangeFrom> trainingRangeFrom = new ObservableField<>(TrainingRangeFrom.ONE);

    public final ObservableField<TrainingRangeTo> trainingRangeTo = new ObservableField<>(TrainingRangeTo.ONE_HUNDRED);

    public final ObservableField<KimarijiFilter> kimariji = new ObservableField<>(KimarijiFilter.ALL);

    public final ObservableField<KarutaStyleFilter> kamiNoKuStyle = new ObservableField<>(KarutaStyleFilter.KANJI);

    public final ObservableField<KarutaStyleFilter> shimoNoKuStyle = new ObservableField<>(KarutaStyleFilter.KANA);

    public final ObservableField<ColorFilter> color = new ObservableField<>(ColorFilter.ALL);

    private final PublishSubject<Unit> onClickStartTrainingEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickStartTrainingEvent = onClickStartTrainingEventSubject;

    private final PublishSubject<Unit> invalidTrainingRangeEventSubject = PublishSubject.create();
    public final Observable<Unit> invalidTrainingRangeEvent = invalidTrainingRangeEventSubject;

    @SuppressWarnings("unused")
    public void onClickStartTraining(View view) {

        if (trainingRangeFrom.get().ordinal() > trainingRangeTo.get().ordinal()) {
            invalidTrainingRangeEventSubject.onNext(Unit.INSTANCE);
            return;
        }

        onClickStartTrainingEventSubject.onNext(Unit.INSTANCE);
    }
}
