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

package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.dialog.di;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.dialog.ConfirmMaterialEditDialogFragmentViewModel;

@Module
public class ConfirmMaterialEditDialogFragmentViewModelModule {

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

    public ConfirmMaterialEditDialogFragmentViewModelModule(@NonNull String firstPhraseKanji,
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

    @Provides
    @ForFragment
    ConfirmMaterialEditDialogFragmentViewModel.Factory provideFactory() {
        return new ConfirmMaterialEditDialogFragmentViewModel.Factory(firstPhraseKanji,
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
}
