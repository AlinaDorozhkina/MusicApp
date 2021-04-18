package ru.alinadorozhkina.musicapp.di.module

import dagger.Module
import dagger.Provides
import ru.alinadorozhkina.musicapp.ui.App

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App = app

}