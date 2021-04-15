package ru.alinadorozhkina.musicapp.mvp.model.repo

import android.util.Log
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.alinadorozhkina.musicapp.api.IDataSource
import ru.alinadorozhkina.musicapp.mvp.model.entity.Chart
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.RoomTrack
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.db.DataBase
import ru.alinadorozhkina.musicapp.mvp.model.network.INetworkStatus

class RetrofitTopTracksRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val db: DataBase
) : ITopTracksRepo {
    // override fun getTopTracks() = api.getTopTrack().subscribeOn(Schedulers.io())


    override fun getTopTracks() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getTopTrack()
                .flatMap { chart ->
                    Single.fromCallable {
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
                        chart
                    }
                }
        } else {
            Single.fromCallable {
                db.chartTracksDao.getChartTracks().map {
                    Track(it.id, it.title, it.position, it.artist)
                }.let { trackList ->
                    Chart(trackList.sortedBy { it.position }, trackList.size)
                }
            }
        }
    }.subscribeOn(Schedulers.io())

}