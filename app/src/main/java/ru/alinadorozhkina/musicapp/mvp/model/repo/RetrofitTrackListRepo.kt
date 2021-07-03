package ru.alinadorozhkina.musicapp.mvp.model.repo

import android.util.Log
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.alinadorozhkina.musicapp.api.IDataSource
import ru.alinadorozhkina.musicapp.mvp.model.cache.ITrackListCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrackList
import ru.alinadorozhkina.musicapp.mvp.model.network.INetworkStatus

class RetrofitTrackListRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val cache: ITrackListCache
) : ITrackListRepo {

    lateinit var artist1: Artist

    override fun getTrackList(artist: Artist) = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            artist.tracklist.let { url ->
                Log.v("URL", url)
                api.getTrackList(url)
                    .flatMap { artistTrackList ->
                        cache.putArtistTrackList(artist, artistTrackList)
                            .toSingleDefault(artistTrackList)
                    }
            }
        } else {
            cache.getArtistTrackList(artist)
        }
    }.subscribeOn(Schedulers.io())


    override  fun getTrackList(artist: String?): Single<ArtistTrackList> = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            artist.let { name ->
           api.getArtistTracks(name)
               .flatMap { artistTrackList ->
                   Log.v("GettrackList",artistTrackList.data.toString() )
                   artist1 = artistTrackList.data[0].artist
                   cache.putArtistTrackList(artistTrackList.data[0].artist, artistTrackList)
                       .toSingleDefault(artistTrackList)
               }
            }
} else {
            cache.getArtistTrackList(artist1)
        }
    }.subscribeOn(Schedulers.io())

}