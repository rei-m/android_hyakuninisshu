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

package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.adapter;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.MaterialKarutaListAdapter;

public class KarutaListItemViewModel {

    public final ObservableInt karutaNo = new ObservableInt(0);

    public final ObservableField<String> karutaImageNo = new ObservableField<>("");

    public final ObservableField<String> creator = new ObservableField<>("");

    public final ObservableField<String> kamiNoKu = new ObservableField<>("");

    public final ObservableField<String> shimoNoKu = new ObservableField<>("");

    private int position = 0;

    private MaterialKarutaListAdapter.OnItemInteractionListener listener;

    @Inject
    public KarutaListItemViewModel() {
    }

    public void setKaruta(@NonNull Karuta karuta) {
        this.karutaNo.set(karuta.identifier().value());
        this.karutaImageNo.set(karuta.imageNo().value());
        this.creator.set(karuta.creator());
        this.kamiNoKu.set(karuta.kamiNoKu().kanji());
        this.shimoNoKu.set(karuta.shimoNoKu().kanji());
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setListener(MaterialKarutaListAdapter.OnItemInteractionListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("unused")
    public void onItemClicked(View view) {
        listener.onItemClicked(position);
    }
}
