package ru.alinadorozhkina.musicapp.mvp.model.cache.room

import android.util.Log
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.alinadorozhkina.musicapp.mvp.model.cache.ITopTracksCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.Chart
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.RoomTrack
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.db.DataBase

class RoomTopTrackCache(val db: DataBase) : ITopTracksCache {

    override fun getTracks() = Single.fromCallable {
        db.chartTracksDao.getChartTracks().map {
            Track(it.id, it.title, it.position, it.artist, it.album, it.preview, it.title_short, it.explicit_lyrics)
        }.let { trackList ->
            Chart(trackList.sortedBy { it.position }, trackList.size)
        }
    }.subscribeOn(Schedulers.io())

    override fun putTracks(chart: Chart) = Completable.fromAction {
        val roomTracks: List<RoomTrack> = chart.data.map {
            RoomTrack(it.id, it.title, it.position, it.title_short, it.explicit_lyrics, it.artist, it.album, it.preview)
        }
        db.chartTracksDao.getChartTracks().let {
            if (it.isEmpty()) {
                db.chartTracksDao.insert(roomTracks)
                Log.v("RetrofitTopTracks", "it.isEmpty - insert ")
                Log.v("RetrofitTopTracks", roomTracks.toString())
            } else {
                db.chartTracksDao.update(roomTracks)
                Log.v("RetrofitTopTracks", "it.is not Empty - update ")
                Log.v("RetrofitTopTracks", roomTracks.toString())
            }
        }
    }.subscribeOn(Schedulers.io())

}