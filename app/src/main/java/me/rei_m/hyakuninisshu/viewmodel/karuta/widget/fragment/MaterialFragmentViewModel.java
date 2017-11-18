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
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;

public class MaterialFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final KarutaModel karutaModel;

        public Factory(@NonNull KarutaModel karutaModel) {
            this.karutaModel = karutaModel;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public MaterialFragmentViewModel create(@NonNull Class modelClass) {
            return new MaterialFragmentViewModel(karutaModel);
        }
    }

    public final ObservableArrayList<Karuta> karutaList = new ObservableArrayList<>();

    private final KarutaModel karutaModel;

    private ColorFilter colorFilter = ColorFilter.ALL;

    private CompositeDisposable disposable = null;

    public MaterialFragmentViewModel(@NonNull KarutaModel karutaModel) {
        this.karutaModel = karutaModel;
        disposable = new CompositeDisposable();
        disposable.addAll(karutaModel.karuta.subscribe(karuta -> {
            if (karutaList.contains(karuta)) {
                int index = karutaList.indexOf(karuta);
                karutaList.set(index, karuta);
            } else {
                karutaList.add(karuta);
            }
        }));
        this.karutaModel.fetchKarutas(colorFilter.value());
    }

    @Override
    protected void onCleared() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onCleared();
    }

    public void onOptionItemSelected(@NonNull ColorFilter colorFilter) {
        karutaList.clear();
        karutaModel.fetchKarutas(colorFilter.value());
        this.colorFilter = colorFilter;
    }
}
