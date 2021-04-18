package ru.alinadorozhkina.musicapp.di.module

import dagger.Module
import dagger.Provides
import ru.alinadorozhkina.musicapp.api.IDataSource
import ru.alinadorozhkina.musicapp.mvp.model.cache.ITopTracksCache
import ru.alinadorozhkina.musicapp.mvp.model.network.INetworkStatus
import ru.alinadorozhkina.musicapp.mvp.model.repo.ITopTracksRepo
import ru.alinadorozhkina.musicapp.mvp.model.repo.RetrofitTopTracksRepo

@Module
class TopTracksModule {

    @Provides
    fun topTracks(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: ITopTracksCache
    ): ITopTracksRepo = RetrofitTopTracksRepo(api, networkStatus, cache)

}