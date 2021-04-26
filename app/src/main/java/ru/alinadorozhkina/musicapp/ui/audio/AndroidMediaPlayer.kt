package ru.alinadorozhkina.musicapp.ui.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import ru.alinadorozhkina.musicapp.mvp.model.audio.IAudioPlayer

class AndroidMediaPlayer(val context: Context) : IAudioPlayer<Int> {
    private val publishSubject = PublishSubject.create<Int>()
    override val observable: Observable<Int> = publishSubject
    private var mediaPlayer: MediaPlayer? = null

    override fun create(song: String) = Completable.create { emitter ->
        try {
            mediaPlayer?.reset()
            mediaPlayer = MediaPlayer.create(context, Uri.parse(song))
            mediaPlayer?.start()
            emitter.onComplete()
        } catch (t: Throwable) {
            emitter.onError(t)
        }
    }.subscribeOn(Schedulers.io())

    override fun start() = Observable.create<Int> { emitter ->
        try {
            while (mediaPlayer?.isPlaying!!) {
                Thread.sleep(1000)
//                Log.v("Tag", "" + Thread.currentThread().name)
//                Log.v("Tag", "" + mediaPlayer?.currentSeconds)
                emitter.onNext(mediaPlayer?.currentSeconds)
            }
        } catch (t: Throwable) {
            emitter.onError(RuntimeException(t.message.toString()))
        }
        emitter.onComplete()
    }.subscribeOn(Schedulers.io())

    override fun getDuration() = Single.fromCallable {
        mediaPlayer?.seconds ?: 0
    }.subscribeOn(Schedulers.io())

    override fun stop() {
        mediaPlayer?.stop()
    }

    override fun clear() {
        mediaPlayer?.release()
        mediaPlayer = null
    }


    val MediaPlayer.seconds: Int
        get() {
            return this.duration / 1000
        }


    val MediaPlayer.currentSeconds: Int
        get() {
            return this.currentPosition / 1000
        }

}

