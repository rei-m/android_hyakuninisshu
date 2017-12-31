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

package me.rei_m.hyakuninisshu.viewmodel.karuta;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.action.material.MaterialActionDispatcher;
import me.rei_m.hyakuninisshu.store.MaterialStore;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;

public class MaterialDetailActivityViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final MaterialStore materialStore;
        private final MaterialActionDispatcher actionDispatcher;
        private final ColorFilter colorFilter;

        public Factory(@NonNull MaterialStore materialStore,
                       @NonNull MaterialActionDispatcher actionDispatcher,
                       @NonNull ColorFilter colorFilter) {
            this.materialStore = materialStore;
            this.actionDispatcher = actionDispatcher;
            this.colorFilter = colorFilter;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MaterialDetailActivityViewModel.class)) {
                return (T) new MaterialDetailActivityViewModel(materialStore, actionDispatcher, colorFilter);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableArrayList<Karuta> karutaList = new ObservableArrayList<>();

    private final CompositeDisposable disposable = new CompositeDisposable();

    public MaterialDetailActivityViewModel(@NonNull MaterialStore materialStore,
                                           @NonNull MaterialActionDispatcher actionDispatcher,
                                           @NonNull ColorFilter colorFilter) {
        disposable.addAll(materialStore.karutaList.subscribe(karutaList -> {
            this.karutaList.clear();
            this.karutaList.addAll(karutaList);
        }));

        actionDispatcher.fetch(colorFilter);
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
