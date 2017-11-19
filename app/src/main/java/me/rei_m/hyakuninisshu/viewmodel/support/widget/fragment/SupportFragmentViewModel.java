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

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.util.Unit;

public class SupportFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final String version;

        public Factory(@NonNull String version) {
            this.version = version;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public SupportFragmentViewModel create(@NonNull Class modelClass) {
            return new SupportFragmentViewModel(version);
        }
    }

    public final ObservableField<String> version = new ObservableField<>();

    private final PublishSubject<Unit> onClickReviewEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickReviewEvent = onClickReviewEventSubject;

    private final PublishSubject<Unit> onClickLicenseEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickLicenseEvent = onClickLicenseEventSubject;

    public SupportFragmentViewModel(@NonNull String version) {
        this.version.set(version);
    }

    @SuppressWarnings("unused")
    public void onClickReview(View view) {
        onClickReviewEventSubject.onNext(Unit.INSTANCE);
    }

    @SuppressWarnings("unused")
    public void onClickLicense(View view) {
        onClickLicenseEventSubject.onNext(Unit.INSTANCE);
    }
}
