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

package me.rei_m.hyakuninisshu.viewmodel.support.widget.fragment.di;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.BuildConfig;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.viewmodel.support.widget.fragment.SupportFragmentViewModel;

@Module
public class SupportFragmentViewModelModule {
    @Provides
    SupportFragmentViewModel.Factory provideFactory(@NonNull Context context) {
        String version = context.getString(R.string.version, BuildConfig.VERSION_NAME);
        return new SupportFragmentViewModel.Factory(version);
    }
}
