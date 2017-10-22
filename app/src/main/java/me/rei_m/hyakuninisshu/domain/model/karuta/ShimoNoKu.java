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

public class ShimoNoKu extends AbstractEntity<ShimoNoKu, ShimoNoKuIdentifier> {

    private final Phrase fourth;

    private final Phrase fifth;

    public ShimoNoKu(@NonNull ShimoNoKuIdentifier identifier,
                     @NonNull Phrase fourth,
                     @NonNull Phrase fifth) {
        super(identifier);
        this.fourth = fourth;
        this.fifth = fifth;
    }

    public Phrase fourth() {
        return fourth;
    }

    public Phrase fifth() {
        return fifth;
    }
    
    @Override
    public String toString() {
        return "ShimoNoKu{" +
                "fourth=" + fourth +
                ", fifth=" + fifth +
                "} " + super.toString();
    }
}
