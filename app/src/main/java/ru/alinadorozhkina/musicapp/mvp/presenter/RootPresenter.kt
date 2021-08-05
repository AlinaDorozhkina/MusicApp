package ru.alinadorozhkina.musicapp.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.di.LocalCiceroneHolder
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens
import ru.alinadorozhkina.musicapp.mvp.views.RootView
import javax.inject.Inject

class RootPresenter: MvpPresenter<RootView>() {


    fun fragmentChartClicked() {

    }

    fun fragmentFavouritesClicked() {
    }

    fun fragmentGenreClicked() {


    }
}