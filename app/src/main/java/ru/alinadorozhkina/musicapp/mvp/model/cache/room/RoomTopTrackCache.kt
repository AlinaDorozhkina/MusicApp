package ru.alinadorozhkina.musicapp.mvp.model.cache.room


import android.util.Log
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.db.DataBase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.alinadorozhkina.musicapp.mvp.model.cache.ITopTracksCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.Chart
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.RoomTrack

class RoomTopTrackCache(val db: DataBase) : ITopTracksCache {
    override fun getTracks() = Single.fromCallable {
        db.chartTracksDao.getChartTracks().map {
            Track(it.id, it.title, it.position, it.artist)
        }.let { trackList ->
            Chart(trackList.sortedBy { it.position }, trackList.size)
        }
    }.subscribeOn(Schedulers.io())

    override fun putTracks(chart: Chart) = Completable.fromAction{
        val roomTracks: List<RoomTrack> = chart.data.map {
            RoomTrack(it.id, it.title, it.position, it.artist)
        }
        db.chartTracksDao.getChartTracks().let {
            if (it.isEmpty()){
                db.chartTracksDao.insert(roomTracks)
                Log.v("RetrofitTopTracks", "it.isEmpty - insert ")
                Log.v("RetrofitTopTracks", roomTracks.toString())
            }else {
                db.chartTracksDao.update(roomTracks)
                Log.v("RetrofitTopTracks", "it.is not Empty - update ")
                Log.v("RetrofitTopTracks", roomTracks.toString())
            }
        }
    }.subscribeOn(Schedulers.io())
}