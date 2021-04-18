package ru.alinadorozhkina.musicapp.di.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens
import ru.alinadorozhkina.musicapp.ui.navigation.AndroidScreens

@Module
class CiceroneModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    fun navigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    fun router(): Router = cicerone.router

    @Provides
    fun screens(): IScreens = AndroidScreens()
}