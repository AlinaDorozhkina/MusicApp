package ru.alinadorozhkina.musicapp.ui.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens
import ru.alinadorozhkina.musicapp.ui.fragments.*

class AndroidScreens : IScreens {

    override fun topList() = FragmentScreen { TopTracksFragment.newInstance() }
    override fun artist(artist: Artist) = FragmentScreen { ArtistInfoFragment.newInstance(artist) }
    override fun settings(): Screen = FragmentScreen { SettingsFragment.newInstance()}
    override fun search(name: String): Screen = FragmentScreen { SearchFragment.newInstance(name)}

}