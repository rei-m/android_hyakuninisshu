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
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas;
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

    private final KarutaModel karutaModel;

    private Karutas karutas;

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final Observable.OnPropertyChangedCallback colorFilterChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            if (karutas == null) {
                karutaModel.fetchKarutas();
            } else {
                karutaList.clear();
                karutaList.addAll(karutas.asList(colorFilter.get().value()));
            }
        }
    };

    public MaterialFragmentViewModel(@NonNull KarutaModel karutaModel,
                                     @NonNull ColorFilter colorFilter) {
        this.karutaModel = karutaModel;
        this.colorFilter = new ObservableField<>(colorFilter);
        this.colorFilter.addOnPropertyChangedCallback(colorFilterChangedCallback);
        disposable.addAll(karutaModel.karutas.subscribe(karutas -> {
            this.karutas = karutas;
            this.karutaList.clear();
            this.karutaList.addAll(karutas.asList(MaterialFragmentViewModel.this.colorFilter.get().value()));
        }));
        karutaModel.fetchKarutas();
    }

    @Override
    protected void onCleared() {
        colorFilter.removeOnPropertyChangedCallback(colorFilterChangedCallback);
        disposable.dispose();
        super.onCleared();
    }
}
