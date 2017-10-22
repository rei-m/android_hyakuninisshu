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

package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class YomiFuda implements ValueObject {

    private final String firstPhrase;

    private final String secondPhrase;

    private final String thirdPhrase;

    public YomiFuda(@NonNull String firstPhrase, @NonNull String secondPhrase, @NonNull String thirdPhrase) {
        this.firstPhrase = firstPhrase;
        this.secondPhrase = secondPhrase;
        this.thirdPhrase = thirdPhrase;
    }

    public String firstPhrase() {
        return firstPhrase;
    }

    public String secondPhrase() {
        return secondPhrase;
    }

    public String thirdPhrase() {
        return thirdPhrase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YomiFuda yomiFuda = (YomiFuda) o;

        return firstPhrase.equals(yomiFuda.firstPhrase) &&
                secondPhrase.equals(yomiFuda.secondPhrase) &&
                thirdPhrase.equals(yomiFuda.thirdPhrase);
    }

    @Override
    public int hashCode() {
        int result = firstPhrase.hashCode();
        result = 31 * result + secondPhrase.hashCode();
        result = 31 * result + thirdPhrase.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "YomiFuda{" +
                "firstPhrase='" + firstPhrase + '\'' +
                ", secondPhrase='" + secondPhrase + '\'' +
                ", thirdPhrase='" + thirdPhrase + '\'' +
                '}';
    }
}
