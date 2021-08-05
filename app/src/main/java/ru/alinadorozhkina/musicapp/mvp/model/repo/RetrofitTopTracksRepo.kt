package ru.alinadorozhkina.musicapp.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.alinadorozhkina.musicapp.api.IDataSource
import ru.alinadorozhkina.musicapp.mvp.model.cache.ITopTracksCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.Chart
import ru.alinadorozhkina.musicapp.mvp.model.network.INetworkStatus

class RetrofitTopTracksRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val cache: ITopTracksCache
) : ITopTracksRepo {

    override fun getTopTracks() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getTopTrack()
                .flatMap { chart ->
                    cache.putTracks(chart).toSingleDefault(chart)
                }
        } else {
            cache.getTracks()
        }
    }.subscribeOn(Schedulers.io())

}