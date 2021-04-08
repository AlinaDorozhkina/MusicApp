package ru.alinadorozhkina.musicapp.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import ru.alinadorozhkina.musicapp.mvp.model.entity.Chart

interface ITopTracksRepo {
    fun getTopTracks(): Single<Chart>
}