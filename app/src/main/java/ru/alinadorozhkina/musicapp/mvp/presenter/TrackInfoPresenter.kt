package ru.alinadorozhkina.musicapp.mvp.presenter

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.audio.IAudioPlayer
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track
import ru.alinadorozhkina.musicapp.mvp.views.TrackInfoView
import javax.inject.Inject
import javax.inject.Named

class TrackInfoPresenter(val track: Track): MvpPresenter<TrackInfoView>() {
    @Inject lateinit var audioPlayer: IAudioPlayer<Int>
    @field:Named("ui-thread")
    @Inject lateinit var uiScheduler: Scheduler

    val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setTrackTitle(track.title)
        viewState.setArtistName(track.artist.name)
        viewState.setAlbumTitle(track.album.title)
    }

    fun buttonPlayClicked() {
        val disposable = audioPlayer.create(track.preview)
            .observeOn(uiScheduler)
            .subscribe({
                       audioPlayer.start()

            },{

            })
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        audioPlayer.clear()
    }


}