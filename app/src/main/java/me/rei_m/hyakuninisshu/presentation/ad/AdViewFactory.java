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

package me.rei_m.hyakuninisshu.presentation.ad;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.rei_m.hyakuninisshu.R;

@Singleton
public class AdViewFactory {

    private final Context context;

    @Inject
    public AdViewFactory(@NonNull Context context) {
        this.context = context;
    }

    public AdView create() {
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(context.getString(R.string.banner_ad_unit_id));
        return adView;
    }
}
