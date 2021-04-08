package ru.alinadorozhkina.musicapp.mvp.model.repo

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.alinadorozhkina.musicapp.api.IDataSource

class RetrofitTopTracksRepo(val api: IDataSource) : ITopTracksRepo {
    override fun getTopTracks() = api.getTopTrack().subscribeOn(Schedulers.io())
}