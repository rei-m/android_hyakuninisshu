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

import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class MaterialFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableArrayList<Karuta> karutaList = new ObservableArrayList<>();

    private final KarutaModel karutaModel;

    private final AnalyticsManager analyticsManager;

    private ColorFilter colorFilter = ColorFilter.ALL;

    public MaterialFragmentViewModel(@NonNull KarutaModel karutaModel,
                                     @NonNull AnalyticsManager analyticsManager) {
        this.karutaModel = karutaModel;
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaModel.completeFetchKarutasEvent.subscribe(karutaList -> {
            this.karutaList.clear();
            this.karutaList.addAll(karutaList);
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.MATERIAL);
        karutaModel.fetchKarutas(colorFilter.value());
    }

    public void onOptionItemSelected(@NonNull ColorFilter colorFilter) {
        karutaModel.fetchKarutas(colorFilter.value());
        this.colorFilter = colorFilter;
    }
}
