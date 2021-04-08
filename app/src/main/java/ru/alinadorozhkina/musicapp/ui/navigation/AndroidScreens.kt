package ru.alinadorozhkina.musicapp.ui.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens
import ru.alinadorozhkina.musicapp.ui.fragments.ArtistFragment
import ru.alinadorozhkina.musicapp.ui.fragments.ArtistTracksFragment
import ru.alinadorozhkina.musicapp.ui.fragments.TopTracksFragment

class AndroidScreens : IScreens {
    override fun topList() = FragmentScreen { TopTracksFragment.newInstance() }
    override fun artist(artist: Artist) = FragmentScreen { ArtistFragment.newInstance(artist) }
    override fun artistTracks(url: String): Screen = FragmentScreen { ArtistTracksFragment.newInstance(url) }
}