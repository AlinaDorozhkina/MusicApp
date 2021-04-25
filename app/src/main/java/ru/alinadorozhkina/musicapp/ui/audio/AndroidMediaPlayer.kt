package ru.alinadorozhkina.musicapp.ui.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import ru.alinadorozhkina.musicapp.mvp.model.audio.IAudioPlayer
import java.lang.Exception
import javax.inject.Inject

class AndroidMediaPlayer(val context: Context) : IAudioPlayer {
    private var mediaPlayer: MediaPlayer? = null
    private val publishSubject = PublishSubject.create<Int>()
    //override val observable: Observable<Int> = publishSubject

    override fun create(song: String) = Single.fromCallable {

        mediaPlayer?.reset()
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            this.setDataSource(context, Uri.parse(song))
            prepare()
            start()
        }
        mediaPlayer?.duration?:0
//        var count = 0
//        while (count < mediaPlayer?.duration?.div(1000) ?: 0) {
//            emetter.onNext(count)
//            count++
//            Thread.sleep(1000)
//
//        }


        //      mediaPlayer = MediaPlayer().apply {
//            setAudioAttributes(
//                AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .setUsage(AudioAttributes.USAGE_MEDIA)
//                    .build()
//            )
//            this.create(context, Uri.parse(song))
//            prepare()
//            start()

//        try{
//        mediaPlayer?.currentPosition
//       // mediaPlayer?.currentSeconds?.let { mediaPlayer?.seconds?.minus(it)}
//
//        }catch (e: Exception) {
//            Log.v("error", e.message.toString())
//    }
    }.subscribeOn(Schedulers.io())
    //mediaPlayer?.currentSeconds?.let { mediaPlayer?.seconds?.minus(it) }


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

//    override fun currentPosition(): PublishSubject<Int> {
//        TODO("Not yet implemented")
//    }
//    private val publishSubject = PublishSubject.create<Int>()
//    val observable: Observable<Int> = publishSubject
    // private var mediaPlayer: MediaPlayer? = null
    // override var observable = PublishSubject.create<Int>()


//

//
//        mediaPlayer?.duration?:0
//
//
//    //override fun start(): Observable<Int> = Observable.just(mediaPlayer?.currentPosition)
//
//
//    override fun stop() {
//        mediaPlayer?.stop()
//
//    }
//
//    override fun clear() {
//        mediaPlayer?.release()
//        mediaPlayer = null
//    }
//
//    override fun run (){
//        publishSubject.onNext(mediaPlayer?.currentPosition)
//        publishSubject.subscribe {
//            mediaPlayer?.seekTo(it)
//        }
//    }

}