package ru.alinadorozhkina.musicapp.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.alinadorozhkina.musicapp.mvp.model.entity.Chart

interface ITopTracksCache {

    fun getTracks(): Single<Chart>
    fun putTracks(chart: Chart): Completable

}