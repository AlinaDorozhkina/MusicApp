package ru.alinadorozhkina.musicapp.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.view.MainView
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens
import javax.inject.Inject

class MainPresenter() : MvpPresenter<MainView>() {

    @Inject lateinit var router: Router
    @Inject lateinit var screens: IScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.topList())
    }
}