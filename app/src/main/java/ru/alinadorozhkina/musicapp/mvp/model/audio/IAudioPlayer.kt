package ru.alinadorozhkina.musicapp.mvp.model.audio

import io.reactivex.rxjava3.core.Completable

interface IAudioPlayer {
    fun start(song: String): Completable
    fun stop()
    fun clear()
}