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
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;

public class MaterialFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final KarutaModel karutaModel;

        private ColorFilter colorFilter = ColorFilter.ALL;

        public Factory(@NonNull KarutaModel karutaModel) {
            this.karutaModel = karutaModel;
        }

        public void setColorFilter(@NonNull ColorFilter colorFilter) {
            this.colorFilter = colorFilter;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MaterialFragmentViewModel.class)) {
                return (T) new MaterialFragmentViewModel(karutaModel, colorFilter);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableArrayList<Karuta> karutaList = new ObservableArrayList<>();

    public final ObservableField<ColorFilter> colorFilter;

    private CompositeDisposable disposable = null;

    private Observable.OnPropertyChangedCallback colorFilterChangedCallback;

    public MaterialFragmentViewModel(@NonNull KarutaModel karutaModel,
                                     @NonNull ColorFilter colorFilter) {
        colorFilterChangedCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                karutaModel.fetchKarutas(MaterialFragmentViewModel.this.colorFilter.get().value());
            }
        };
        this.colorFilter = new ObservableField<>(colorFilter);
        this.colorFilter.addOnPropertyChangedCallback(colorFilterChangedCallback);
        disposable = new CompositeDisposable();
        disposable.addAll(karutaModel.karutaList.subscribe(karutaList -> {
            this.karutaList.clear();
            this.karutaList.addAll(karutaList);
        }));
        karutaModel.fetchKarutas(MaterialFragmentViewModel.this.colorFilter.get().value());
    }

    @Override
    protected void onCleared() {
        if (colorFilterChangedCallback != null) {
            colorFilter.removeOnPropertyChangedCallback(colorFilterChangedCallback);
            colorFilterChangedCallback = null;
        }
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onCleared();
    }
}
