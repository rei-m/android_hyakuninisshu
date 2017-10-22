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

package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class Karuta extends AbstractEntity<Karuta, KarutaIdentifier> {

    public static final int NUMBER_OF_KARUTA = 100;

    private final String creator;

    private KamiNoKu kamiNoKu;

    private ShimoNoKu shimoNoKu;

    private final Kimariji kimariji;

    private final ImageNo imageNo;

    private final String translation;

    private final Color color;

    public Karuta(@NonNull KarutaIdentifier identifier,
                  @NonNull String creator,
                  @NonNull KamiNoKu kamiNoKu,
                  @NonNull ShimoNoKu shimoNoKu,
                  @NonNull Kimariji kimariji,
                  @NonNull ImageNo imageNo,
                  @NonNull String translation,
                  @NonNull Color color) {
        super(identifier);
        this.creator = creator;
        this.kamiNoKu = kamiNoKu;
        this.shimoNoKu = shimoNoKu;
        this.kimariji = kimariji;
        this.imageNo = imageNo;
        this.translation = translation;
        this.color = color;
    }

    public String creator() {
        return creator;
    }

    public KamiNoKu kamiNoKu() {
        return kamiNoKu;
    }

    public ShimoNoKu shimoNoKu() {
        return shimoNoKu;
    }

    public Kimariji kimariji() {
        return kimariji;
    }

    public ImageNo imageNo() {
        return imageNo;
    }

    public String translation() {
        return translation;
    }

    public Color color() {
        return color;
    }

    public Karuta updatePhrase(@NonNull String firstPhraseKanji,
                               @NonNull String firstPhraseKana,
                               @NonNull String secondPhraseKanji,
                               @NonNull String secondPhraseKana,
                               @NonNull String thirdPhraseKanji,
                               @NonNull String thirdPhraseKana,
                               @NonNull String fourthPhraseKanji,
                               @NonNull String fourthPhraseKana,
                               @NonNull String fifthPhraseKanji,
                               @NonNull String fifthPhraseKana) {

        KamiNoKu kamiNoKu = new KamiNoKu(
                this.kamiNoKu.identifier(),
                new Phrase(firstPhraseKana, firstPhraseKanji),
                new Phrase(secondPhraseKana, secondPhraseKanji),
                new Phrase(thirdPhraseKana, thirdPhraseKanji)
        );

        ShimoNoKu shimoNoKu = new ShimoNoKu(
                this.shimoNoKu.identifier(),
                new Phrase(fourthPhraseKana, fourthPhraseKanji),
                new Phrase(fifthPhraseKana, fifthPhraseKanji)
        );

        this.kamiNoKu = kamiNoKu;
        this.shimoNoKu = shimoNoKu;

        return this;
    }

    @Override
    public String toString() {
        return "Karuta{" +
                "creator='" + creator + '\'' +
                ", kamiNoKu=" + kamiNoKu +
                ", shimoNoKu=" + shimoNoKu +
                ", kimariji=" + kimariji +
                ", imageNo=" + imageNo +
                ", translation='" + translation + '\'' +
                ", color=" + color +
                "} " + super.toString();
    }
}
