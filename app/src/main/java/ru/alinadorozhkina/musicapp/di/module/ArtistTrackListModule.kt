package ru.alinadorozhkina.musicapp.di.module

import dagger.Module
import dagger.Provides
import ru.alinadorozhkina.musicapp.api.IDataSource
import ru.alinadorozhkina.musicapp.mvp.model.cache.ITrackListCache
import ru.alinadorozhkina.musicapp.mvp.model.network.INetworkStatus
import ru.alinadorozhkina.musicapp.mvp.model.repo.ITrackListRepo
import ru.alinadorozhkina.musicapp.mvp.model.repo.RetrofitTrackListRepo

@Module
class ArtistTrackListModule {

    @Provides
    fun trackList(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: ITrackListCache
    ): ITrackListRepo = RetrofitTrackListRepo(api, networkStatus, cache)

}