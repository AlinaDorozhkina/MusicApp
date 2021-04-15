package ru.alinadorozhkina.musicapp.ui

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.db.DataBase

class App: Application() {
    companion object {
        lateinit var instance: App
    }

    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router

    override fun onCreate() {
        super.onCreate()
        instance = this
       DataBase.create(this)
    }
}