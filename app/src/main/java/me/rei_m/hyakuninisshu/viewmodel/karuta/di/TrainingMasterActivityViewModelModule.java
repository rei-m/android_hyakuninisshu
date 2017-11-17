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

package me.rei_m.hyakuninisshu.viewmodel.karuta.di;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeTo;
import me.rei_m.hyakuninisshu.viewmodel.karuta.TrainingMasterActivityViewModel;

@Module
public class TrainingMasterActivityViewModelModule {

    private final TrainingRangeFrom trainingRangeFrom;

    private final TrainingRangeTo trainingRangeTo;

    private final KimarijiFilter kimarijiFilter;

    private final ColorFilter colorFilter;

    public TrainingMasterActivityViewModelModule(@NonNull TrainingRangeFrom trainingRangeFrom,
                                                 @NonNull TrainingRangeTo trainingRangeTo,
                                                 @NonNull KimarijiFilter kimarijiFilter,
                                                 @NonNull ColorFilter colorFilter) {
        this.trainingRangeFrom = trainingRangeFrom;
        this.trainingRangeTo = trainingRangeTo;
        this.kimarijiFilter = kimarijiFilter;
        this.colorFilter = colorFilter;
    }

    @Provides
    @ForActivity
    TrainingMasterActivityViewModel.Factory provideFactory(@NonNull KarutaTrainingModel karutaTrainingModel) {
        return new TrainingMasterActivityViewModel.Factory(karutaTrainingModel,
                trainingRangeFrom,
                trainingRangeTo,
                kimarijiFilter,
                colorFilter);
    }
}
