package ru.alinadorozhkina.musicapp.mvp.model.cache.room

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.alinadorozhkina.musicapp.mvp.model.cache.ITrackListCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrack
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrackList
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.RoomArtist
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.RoomArtistTrack
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.db.DataBase

class RoomTrackListCache(val db: DataBase): ITrackListCache {
    override fun getArtistTrackList(artist: Artist) = Single.fromCallable {
        val roomArtistTrack = artist.name.let {
            db.artistDao.findByName(it)
        }
        db.artistTrackListDao.findForArtist(roomArtistTrack.id.toString()).map {
            ArtistTrack(it.id, it.title, artist, it.album, it.duration)
        }.let {
            ArtistTrackList(it, it.size)
        }
    }.subscribeOn(Schedulers.io())

    override fun putArtistTrackList(artist: Artist, artistTrackList: ArtistTrackList) = Completable.fromAction{
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
    }.subscribeOn(Schedulers.io())

    override fun putArtist(artist: Artist) = Completable.fromAction{
        val art = RoomArtist(artist.id, artist.name, artist.picture, artist.tracklist)
        db.artistDao.insert(art)
    }.subscribeOn(Schedulers.io())



}