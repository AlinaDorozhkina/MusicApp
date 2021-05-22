package ru.alinadorozhkina.musicapp.mvp.navigation

import com.github.terrakok.cicerone.Screen
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist

interface IScreens {
    fun topList(): Screen
    fun artist(artist: Artist): Screen
    fun artistTracks(artist: Artist): Screen
    fun settings(): Screen
}