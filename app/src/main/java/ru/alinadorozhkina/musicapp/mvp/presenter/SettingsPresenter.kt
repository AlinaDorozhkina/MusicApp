package ru.alinadorozhkina.musicapp.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.alinadorozhkina.helper.Prefs
import ru.alinadorozhkina.musicapp.mvp.views.SettingsView
import javax.inject.Inject

class SettingsPresenter: MvpPresenter<SettingsView>() {
    @Inject lateinit var prefs: Prefs
    @Inject lateinit var router: Router

    fun pinkTheme(){
        prefs.themeColor = "pink"
    }
    fun blueTheme(){
        prefs.themeColor = "blue"
    }
    fun nightTheme(){
        prefs.themeColor = "night"
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}