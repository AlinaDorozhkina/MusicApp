package ru.alinadorozhkina.musicapp.di

import dagger.Component
import ru.alinadorozhkina.musicapp.di.module.*
import ru.alinadorozhkina.musicapp.mvp.presenter.*
import ru.alinadorozhkina.musicapp.ui.activities.MainActivity
import ru.alinadorozhkina.musicapp.ui.adapters.ArtistTracksRVAdapter
import ru.alinadorozhkina.musicapp.ui.adapters.TopTracksRVAdapter
import ru.alinadorozhkina.musicapp.ui.fragments.SettingsFragment

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
        AudioModule::class
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

}