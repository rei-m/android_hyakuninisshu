/*
 * Copyright (c) 2018. Rei Matsushita
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

package me.rei_m.hyakuninisshu.presentation.di

import android.content.Context
import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import me.rei_m.hyakuninisshu.di.ForActivity
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import javax.inject.Named

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ForActivity
    @Named("activityContext")
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideNavigator(): Navigator {
        return Navigator(activity)
    }
}
