package ru.alinadorozhkina.musicapp.ui.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens
import ru.alinadorozhkina.musicapp.ui.fragments.*
import ru.alinadorozhkina.musicapp.ui.fragments.tabsFragments.ArtistInfoFragment
import ru.alinadorozhkina.musicapp.ui.fragments.tabsFragments.FragmentWithTabs
import ru.alinadorozhkina.musicapp.ui.fragments.bottomNavFragments.RootFragment
import ru.alinadorozhkina.musicapp.ui.fragments.bottomNavFragments.TopTracksFragment
import ru.alinadorozhkina.musicapp.ui.fragments.bottomNavFragments.TracksByGenreFragment

class AndroidScreens : IScreens {

    override fun topList() = FragmentScreen { TopTracksFragment.newInstance() }
    override fun settings(): Screen = FragmentScreen { SettingsFragment.newInstance()}
    override fun search(name: String): Screen = FragmentScreen { ArtistInfoFragment.newInstance(name)}
    override fun fragmentWithTabs(track: Track): Screen = FragmentScreen { FragmentWithTabs.newInstance(track)}
    override fun genre(): Screen = FragmentScreen { TracksByGenreFragment.newInstance()}
    override fun root(): Screen = FragmentScreen { RootFragment.newInstance()}
}