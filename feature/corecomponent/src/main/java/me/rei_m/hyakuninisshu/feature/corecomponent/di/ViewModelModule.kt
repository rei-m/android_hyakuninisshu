package me.rei_m.hyakuninisshu.feature.corecomponent.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class ViewModelModule {
    @Provides
    @Singleton
    @Named("mainContext")
    fun provideMainContext(): CoroutineContext = Dispatchers.Main

    @Provides
    @Singleton
    @Named("ioContext")
    fun provideIOContext(): CoroutineContext = Dispatchers.IO
}
