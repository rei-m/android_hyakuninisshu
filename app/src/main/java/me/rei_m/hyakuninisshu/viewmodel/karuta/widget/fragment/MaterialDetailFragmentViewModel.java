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

import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaModel;

public class MaterialDetailFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final KarutaModel karutaModel;

        private final KarutaIdentifier karutaId;

        public Factory(@NonNull KarutaModel karutaModel,
                       @NonNull KarutaIdentifier karutaId) {
            this.karutaModel = karutaModel;
            this.karutaId = karutaId;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MaterialDetailFragmentViewModel.class)) {
                return (T) new MaterialDetailFragmentViewModel(karutaModel, karutaId);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableField<Karuta> karuta = new ObservableField<>();

    private final CompositeDisposable disposable = new CompositeDisposable();

    public MaterialDetailFragmentViewModel(@NonNull KarutaModel karutaModel,
                                           @NonNull KarutaIdentifier karutaId) {
        disposable.addAll(karutaModel.karutas.subscribe(karutas -> {
            this.karuta.set(karutas.get(karutaId));
        }));
        karutaModel.fetchKarutas();
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
