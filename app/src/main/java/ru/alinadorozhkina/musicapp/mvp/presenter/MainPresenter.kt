package ru.alinadorozhkina.musicapp.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.view.MainView
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens

class MainPresenter(val router: Router, val screens: IScreens): MvpPresenter<MainView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.topList())
    }
}