package ru.alinadorozhkina.musicapp.ui.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import ru.alinadorozhkina.musicapp.mvp.model.audio.IAudioPlayer

class AndroidMediaPlayer(val context: Context) : IAudioPlayer {
    private var mediaPlayer: MediaPlayer? = null
    private val publishSubject = PublishSubject.create<Int>()
    //override val observable: Observable<Int> = publishSubject

    override fun create(song: String) = Observable.create<Int> { emitter ->
        Log.v("SONG", song)
        Log.v("Tag2", "" + Thread.currentThread().name)

        mediaPlayer?.reset()
        mediaPlayer = MediaPlayer.create(context, Uri.parse(song))
        mediaPlayer?.start()
        while (mediaPlayer?.isPlaying!!) {
            Thread.sleep(1000)
            Log.v("Tag", "" + Thread.currentThread().name)
            Log.v("Tag", "" + mediaPlayer?.currentSeconds)
            emitter.onNext(mediaPlayer?.currentSeconds)
        }
    }.subscribeOn(Schedulers.newThread())

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

