package ru.alinadorozhkina.musicapp.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import ru.alinadorozhkina.musicapp.mvp.model.entity.Genre

interface ITracksByGenreRepo {
    fun getTracksByGenre(): Single<Genre>
}