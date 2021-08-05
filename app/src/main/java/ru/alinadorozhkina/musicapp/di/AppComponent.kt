package ru.alinadorozhkina.musicapp.di

import dagger.Component
import ru.alinadorozhkina.musicapp.di.module.*
import ru.alinadorozhkina.musicapp.mvp.presenter.*
import ru.alinadorozhkina.musicapp.ui.activities.MainActivity
import ru.alinadorozhkina.musicapp.ui.adapters.ArtistTracksRVAdapter
import ru.alinadorozhkina.musicapp.ui.adapters.TopTracksRVAdapter
import ru.alinadorozhkina.musicapp.ui.adapters.TracksByGenreAdapter
import ru.alinadorozhkina.musicapp.ui.fragments.*
import ru.alinadorozhkina.musicapp.ui.fragments.tabsFragments.ArtistInfoFragment
import ru.alinadorozhkina.musicapp.ui.fragments.tabsFragments.TrackInfoFragment
import ru.alinadorozhkina.musicapp.ui.fragments.bottomNavFragments.RootFragment
import ru.alinadorozhkina.musicapp.ui.fragments.bottomNavFragments.TracksByGenreFragment

@SuperScope
@Component(
    modules = [
        AppModule::class,
        CiceroneModule::class,
        TopTracksModule::class,
        ArtistTrackListModule::class,
        ApiModule::class,
        CacheModule::class,
        ImageModule::class,
        AudioModule::class,
        GenreModule::class,
        LocalNavigationModule::class
    ]
)

interface AppComponent {

    fun inject(topTrackPresenter: TopTrackPresenter)
    fun inject(mainActivity: MainActivity)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(mainPresenter: MainPresenter)
    fun inject(artistPresenter: ArtistPresenter)
    fun inject(topTrackRVAdapter: TopTracksRVAdapter)
    fun inject(artistTracksRVAdapter: ArtistTracksRVAdapter)
    fun inject(topTracksListPresenter: TopTrackPresenter.TopTracksListPresenter)
    fun inject(settingsPresenter: SettingsPresenter)
    fun inject(trackInfoPresenter: TrackInfoPresenter)
    fun inject(artistInfoFragment: ArtistInfoFragment)
    fun inject(trackInfoFragment: TrackInfoFragment)
    fun inject(rootPresenter: RootPresenter)
    fun inject (rootFragment: RootFragment)
    fun inject (tracksByGenreAdapter: TracksByGenreAdapter)
    fun inject (tracksByGenreFragment: TracksByGenreFragment)
    fun inject (tracksByGenrePresenter: TracksByGenrePresenter)
    fun inject (tracksByGenreListPresenter: TracksByGenrePresenter.TracksByGenreListPresenter)
    fun inject (fragmentWithTabsPresenter: FragmentWithTabsPresenter)
}