package ru.alinadorozhkina.musicapp.mvp.model.repo

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.alinadorozhkina.musicapp.api.IDataSource
import ru.alinadorozhkina.musicapp.mvp.model.cache.ITrackListCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.network.INetworkStatus


class RetrofitTrackListRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val cache: ITrackListCache
) : ITrackListRepo {
    override fun getTrackList(artist: Artist) = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            artist.tracklist.let { url ->
                api.getTrackList(url)
                    .flatMap { artistTrackList ->
                        cache.putArtist(artist)
                        cache.putArtistTrackList(artist, artistTrackList)
                            .toSingleDefault(artistTrackList)
                    }
            }
        } else {
            cache.getArtistTrackList(artist)
        }
    }.subscribeOn(Schedulers.io())

}