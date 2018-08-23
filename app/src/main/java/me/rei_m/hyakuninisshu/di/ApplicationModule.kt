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

package me.rei_m.hyakuninisshu.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.experimental.CommonPool
import me.rei_m.hyakuninisshu.action.AppDispatcher
import me.rei_m.hyakuninisshu.action.Dispatcher
import javax.inject.Singleton
import kotlin.coroutines.experimental.CoroutineContext

@Module
class ApplicationModule(application: Application) {

    private val context: Context = application.applicationContext

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = CommonPool

    @Provides
    @Singleton
    fun provideDispatcher(): Dispatcher = AppDispatcher(AndroidSchedulers.mainThread())
}
