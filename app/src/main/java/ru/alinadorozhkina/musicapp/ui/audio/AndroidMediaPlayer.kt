package ru.alinadorozhkina.musicapp.ui.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.alinadorozhkina.musicapp.mvp.model.audio.IAudioPlayer
import javax.inject.Inject

class AndroidMediaPlayer(val context: Context): IAudioPlayer {
   @Inject lateinit var mediaPlayer: MediaPlayer

    override fun start(song: String) = Completable.fromAction {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(song)
            prepare() // might take long! (for buffering, etc)
            start()
        }
        }.subscribeOn(Schedulers.io())


    override fun stop() {

    }

    override fun clear() {
        mediaPlayer.release()
    }
}