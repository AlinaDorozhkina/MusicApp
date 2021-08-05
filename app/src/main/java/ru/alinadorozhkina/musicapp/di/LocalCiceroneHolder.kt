package ru.alinadorozhkina.musicapp.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class LocalCiceroneHolder {
    private val cicerone: Cicerone<Router> = Cicerone.create()
    fun getCicerone(): Cicerone<Router> = cicerone

}