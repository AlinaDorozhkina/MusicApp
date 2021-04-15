package ru.alinadorozhkina.musicapp.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.http.Url
import ru.alinadorozhkina.musicapp.api.IDataSource
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrack
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrackList
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.RoomArtist
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.RoomArtistTrack
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.db.DataBase
import ru.alinadorozhkina.musicapp.mvp.model.network.INetworkStatus


class RetrofitTrackListRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val db: DataBase
) : ITrackListRepo {
    override fun getTrackList(artist: Artist) = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            artist.tracklist.let { url ->
                api.getTrackList(url)
                    .flatMap { artistTrackList ->
                        Single.fromCallable {
                            val art = RoomArtist(artist.id, artist.name, artist.picture, artist.tracklist)
                            db.artistDao.insert(art)

                            val roomArtist =
                                artist.name.let { db.artistDao.findByName(it) }
                            val roomArtistTrack = artistTrackList.data.map {
                                RoomArtistTrack(
                                    it.id,
                                    it.title,
                                    it.album,
                                    it.duration,
                                    roomArtist.id.toString()
                                )
                            }
                            db.artistTrackListDao.getArtistTracks().let {
                                if (it.isEmpty()) {
                                    db.artistTrackListDao.insert(roomArtistTrack)
                                } else {
                                    db.artistTrackListDao.update(roomArtistTrack)
                                }
                            }
                            artistTrackList
                        }
                    }
            }

        } else {
            Single.fromCallable {
                val roomArtistTrack = artist.name.let {
                    db.artistDao.findByName(it)
                }
                db.artistTrackListDao.findForArtist(roomArtistTrack.id.toString()).map {
                    ArtistTrack(it.id, it.title, artist, it.album, it.duration)
                }.let {
                    ArtistTrackList(it, it.size)
                }
            }
        }
    }.subscribeOn(Schedulers.io())

}