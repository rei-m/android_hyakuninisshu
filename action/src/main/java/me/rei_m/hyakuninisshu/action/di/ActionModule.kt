package me.rei_m.hyakuninisshu.action.di

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import me.rei_m.hyakuninisshu.action.AppDispatcher
import me.rei_m.hyakuninisshu.action.Dispatcher
import javax.inject.Singleton

@Module
class ActionModule {
    @Provides
    @Singleton
    fun provideActionDispatcher(): Dispatcher = AppDispatcher(AndroidSchedulers.mainThread())
}
