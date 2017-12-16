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

package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.dialog;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

public class ConfirmMaterialEditDialogFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final String firstPhraseKanji;
        private final String firstPhraseKana;
        private final String secondPhraseKanji;
        private final String secondPhraseKana;
        private final String thirdPhraseKanji;
        private final String thirdPhraseKana;
        private final String fourthPhraseKanji;
        private final String fourthPhraseKana;
        private final String fifthPhraseKanji;
        private final String fifthPhraseKana;

        public Factory(@NonNull String firstPhraseKanji,
                       @NonNull String firstPhraseKana,
                       @NonNull String secondPhraseKanji,
                       @NonNull String secondPhraseKana,
                       @NonNull String thirdPhraseKanji,
                       @NonNull String thirdPhraseKana,
                       @NonNull String fourthPhraseKanji,
                       @NonNull String fourthPhraseKana,
                       @NonNull String fifthPhraseKanji,
                       @NonNull String fifthPhraseKana) {
            this.firstPhraseKanji = firstPhraseKanji;
            this.firstPhraseKana = firstPhraseKana;
            this.secondPhraseKanji = secondPhraseKanji;
            this.secondPhraseKana = secondPhraseKana;
            this.thirdPhraseKanji = thirdPhraseKanji;
            this.thirdPhraseKana = thirdPhraseKana;
            this.fourthPhraseKanji = fourthPhraseKanji;
            this.fourthPhraseKana = fourthPhraseKana;
            this.fifthPhraseKanji = fifthPhraseKanji;
            this.fifthPhraseKana = fifthPhraseKana;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ConfirmMaterialEditDialogFragmentViewModel.class)) {
                return (T) new ConfirmMaterialEditDialogFragmentViewModel(firstPhraseKanji,
                        firstPhraseKana,
                        secondPhraseKanji,
                        secondPhraseKana,
                        thirdPhraseKanji,
                        thirdPhraseKana,
                        fourthPhraseKanji,
                        fourthPhraseKana,
                        fifthPhraseKanji,
                        fifthPhraseKana);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableField<String> firstPhraseKanji = new ObservableField<>();

    public final ObservableField<String> firstPhraseKana = new ObservableField<>();

    public final ObservableField<String> secondPhraseKanji = new ObservableField<>();

    public final ObservableField<String> secondPhraseKana = new ObservableField<>();

    public final ObservableField<String> thirdPhraseKanji = new ObservableField<>();

    public final ObservableField<String> thirdPhraseKana = new ObservableField<>();

    public final ObservableField<String> fourthPhraseKanji = new ObservableField<>();

    public final ObservableField<String> fourthPhraseKana = new ObservableField<>();

    public final ObservableField<String> fifthPhraseKanji = new ObservableField<>();

    public final ObservableField<String> fifthPhraseKana = new ObservableField<>();

    public ConfirmMaterialEditDialogFragmentViewModel(@NonNull String firstPhraseKanji,
                                                      @NonNull String firstPhraseKana,
                                                      @NonNull String secondPhraseKanji,
                                                      @NonNull String secondPhraseKana,
                                                      @NonNull String thirdPhraseKanji,
                                                      @NonNull String thirdPhraseKana,
                                                      @NonNull String fourthPhraseKanji,
                                                      @NonNull String fourthPhraseKana,
                                                      @NonNull String fifthPhraseKanji,
                                                      @NonNull String fifthPhraseKana) {
        this.firstPhraseKanji.set(firstPhraseKanji);
        this.firstPhraseKana.set(firstPhraseKana);
        this.secondPhraseKanji.set(secondPhraseKanji);
        this.secondPhraseKana.set(secondPhraseKana);
        this.thirdPhraseKanji.set(thirdPhraseKanji);
        this.thirdPhraseKana.set(thirdPhraseKana);
        this.fourthPhraseKanji.set(fourthPhraseKanji);
        this.fourthPhraseKana.set(fourthPhraseKana);
        this.fifthPhraseKanji.set(fifthPhraseKanji);
        this.fifthPhraseKana.set(fifthPhraseKana);
    }
}
