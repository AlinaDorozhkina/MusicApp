package ru.alinadorozhkina.musicapp.di

import dagger.Component
import ru.alinadorozhkina.musicapp.di.module.*
import ru.alinadorozhkina.musicapp.mvp.presenter.ArtistPresenter
import ru.alinadorozhkina.musicapp.mvp.presenter.ArtistTracksPresenter
import ru.alinadorozhkina.musicapp.mvp.presenter.MainPresenter
import ru.alinadorozhkina.musicapp.mvp.presenter.TopTrackPresenter
import ru.alinadorozhkina.musicapp.ui.activities.MainActivity

@Component(
    modules = [
        AppModule::class,
        CiceroneModule::class,
        TopTracksModule::class,
        ArtistTrackListModule::class,
        ApiModule::class,
        CacheModule::class
    ]
)

interface AppComponent {
    fun inject(topTrackPresenter: TopTrackPresenter)
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(artistPresenter: ArtistPresenter)
    fun inject(artistTracksPresenter: ArtistTracksPresenter)
}