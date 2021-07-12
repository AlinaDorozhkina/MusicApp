package ru.alinadorozhkina.musicapp.mvp.navigation

import com.github.terrakok.cicerone.Screen
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track

interface IScreens {
    fun topList(): Screen
    fun settings(): Screen
    fun search(name: String): Screen
    fun fragmentWithTabs(track: Track): Screen
    fun genre(): Screen
    fun root(): Screen

}