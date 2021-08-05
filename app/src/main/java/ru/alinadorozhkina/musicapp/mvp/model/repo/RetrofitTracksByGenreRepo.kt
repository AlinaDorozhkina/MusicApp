package ru.alinadorozhkina.musicapp.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.alinadorozhkina.musicapp.api.IDataSource
import ru.alinadorozhkina.musicapp.mvp.model.entity.Genre
import ru.alinadorozhkina.musicapp.mvp.model.entity.Type

class RetrofitTracksByGenreRepo(val api: IDataSource) : ITracksByGenreRepo {
    override fun getTracksByGenre(): Single<Genre> = api.getTracksByGenre().subscribeOn(Schedulers.io())
}