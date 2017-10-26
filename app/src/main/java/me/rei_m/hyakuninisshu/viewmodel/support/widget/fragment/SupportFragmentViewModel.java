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

package me.rei_m.hyakuninisshu.viewmodel.support.widget.fragment;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.util.EventObservable;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class SupportFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableField<String> version = new ObservableField<>();

    public final EventObservable<Unit> onClickLicenseEvent = EventObservable.create();

    private final Navigator navigator;

    private final AnalyticsManager analyticsManager;

    public SupportFragmentViewModel(@NonNull String version,
                                    @NonNull Navigator navigator,
                                    @NonNull AnalyticsManager analyticsManager) {
        this.version.set(version);
        this.navigator = navigator;
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void onResume() {
        super.onResume();
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.SUPPORT);
    }

    @SuppressWarnings("unused")
    public void onClickReview(View view) {
        navigator.navigateToAppStore();
    }

    @SuppressWarnings("unused")
    public void onClickLicense(View view) {
        onClickLicenseEvent.onNext(Unit.INSTANCE);
    }
}
