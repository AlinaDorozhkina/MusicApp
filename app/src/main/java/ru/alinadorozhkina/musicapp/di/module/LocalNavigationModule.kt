package ru.alinadorozhkina.musicapp.di.module

import dagger.Module
import dagger.Provides
import ru.alinadorozhkina.musicapp.di.LocalCiceroneHolder
import javax.inject.Singleton

@Module
class LocalNavigationModule {

    @Provides
    fun provideLocalNavigationHolder(): LocalCiceroneHolder = LocalCiceroneHolder()
}