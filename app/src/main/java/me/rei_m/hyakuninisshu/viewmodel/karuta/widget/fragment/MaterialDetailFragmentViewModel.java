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
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.widget.TextView;

import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.helper.KarutaDisplayHelper;

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
        public MaterialDetailFragmentViewModel create(@NonNull Class modelClass) {
            return new MaterialDetailFragmentViewModel(karutaModel, karutaId);
        }
    }

    public final ObservableInt karutaNo = new ObservableInt();

    public final ObservableField<String> karutaImageNo = new ObservableField<>();

    public final ObservableField<String> creator = new ObservableField<>();

    public final ObservableInt kimariji = new ObservableInt();

    public final ObservableField<String> kamiNoKuKanji = new ObservableField<>();

    public final ObservableField<String> shimoNoKuKanji = new ObservableField<>();

    public final ObservableField<String> kamiNoKuKana = new ObservableField<>();

    public final ObservableField<String> shimoNoKuKana = new ObservableField<>();

    public final ObservableField<String> translation = new ObservableField<>();

    private CompositeDisposable disposable = null;

    public MaterialDetailFragmentViewModel(@NonNull KarutaModel karutaModel,
                                           @NonNull KarutaIdentifier karutaId) {
        disposable = new CompositeDisposable();
        disposable.addAll(karutaModel.karuta.subscribe(karuta -> {
            if (!karuta.identifier().equals(karutaId)) {
                return;
            }
            karutaNo.set(karutaId.value());
            karutaImageNo.set(karuta.imageNo().value());
            creator.set(karuta.creator());
            kimariji.set(karuta.kimariji().value());
            kamiNoKuKanji.set(karuta.kamiNoKu().kanji());
            shimoNoKuKanji.set(karuta.shimoNoKu().kanji());
            kamiNoKuKana.set(karuta.kamiNoKu().kana());
            shimoNoKuKana.set(karuta.shimoNoKu().kana());
            translation.set(karuta.translation());
        }));
        karutaModel.fetchKaruta(karutaId);
    }

    @Override
    protected void onCleared() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onCleared();
    }

    @BindingAdapter({"karutaNo", "creator"})
    public static void setKarutaNoAndCreator(@NonNull TextView view,
                                             int karutaNo,
                                             @NonNull String creator) {

        Context context = view.getContext().getApplicationContext();

        String text = KarutaDisplayHelper.convertNumberToString(context, karutaNo) + " / " + creator;
        view.setText(text);
    }
}
