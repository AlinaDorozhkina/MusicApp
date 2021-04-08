package ru.alinadorozhkina.musicapp.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.http.Url
import ru.alinadorozhkina.musicapp.api.IDataSource
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrack


class RetrofitTrackListRepo(val api: IDataSource): ITrackListRepo{
    override fun getTrackList(@Url url: String) = api.getTrackList(url).subscribeOn(Schedulers.io())
}