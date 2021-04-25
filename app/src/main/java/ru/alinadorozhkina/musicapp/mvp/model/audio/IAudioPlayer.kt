package ru.alinadorozhkina.musicapp.mvp.model.audio

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject

interface IAudioPlayer {
    //val observable:  Observable<T>
    fun create(song: String): Single<Int>
    //fun currentPosition(): PublishSubject<T>
    fun stop()
    fun clear()
}