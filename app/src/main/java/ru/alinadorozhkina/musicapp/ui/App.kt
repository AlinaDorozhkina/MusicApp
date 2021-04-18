package ru.alinadorozhkina.musicapp.ui

import android.app.Application
import ru.alinadorozhkina.musicapp.di.AppComponent
import ru.alinadorozhkina.musicapp.di.DaggerAppComponent
import ru.alinadorozhkina.musicapp.di.module.AppModule

import ru.alinadorozhkina.musicapp.mvp.model.entity.room.db.DataBase

class App: Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
       DataBase.create(this)

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}