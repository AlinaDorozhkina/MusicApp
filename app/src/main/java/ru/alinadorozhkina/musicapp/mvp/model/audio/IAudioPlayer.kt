package ru.alinadorozhkina.musicapp.mvp.model.audio

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface IAudioPlayer<T> {
    val observable:  Observable<T>
    fun start(): Observable<Int>
    fun getDuration(): Single<Int>
    fun stop()
    fun clear()
    fun create(song: String): Completable
}