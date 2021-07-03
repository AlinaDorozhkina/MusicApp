package ru.alinadorozhkina.musicapp.mvp.presenter

import android.graphics.BitmapFactory
import android.util.Log
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.audio.IAudioPlayer
import ru.alinadorozhkina.musicapp.mvp.model.cache.IImageCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrack
import ru.alinadorozhkina.musicapp.mvp.model.repo.ITrackListRepo
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITrackListItemPresenter
import ru.alinadorozhkina.musicapp.mvp.views.ArtistView
import ru.alinadorozhkina.musicapp.mvp.views.list.list.ITrackListItemView
import javax.inject.Inject
import javax.inject.Named

private val TAG = ArtistPresenter::class.java.simpleName
class ArtistPresenter(val artist: String) : MvpPresenter<ArtistView>() {

    @field:Named("ui-thread") @Inject lateinit var uiScheduler: Scheduler
    @Inject lateinit var imageCache: IImageCache
    @Inject lateinit var screens: IScreens
    @Inject lateinit var router: Router
    @field:Named("ui-thread")@Inject lateinit var uiSchedular: Scheduler
    @Inject lateinit var trackListRepoRetrofit: ITrackListRepo
    @Inject lateinit var audioPlayer: IAudioPlayer<Int>

    val trackListPresenter = ArtistTracksListPresenter()
    val compositeDisposable = CompositeDisposable()
    var url: String? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        artist.let {
            viewState.setArtistName(artist)
        }
        viewState.init()
        loadTracks()

        trackListPresenter.itemClickListener = {
            val track: ArtistTrack = trackListPresenter.tracks[it.pos]
            viewState.playArtistTrack(track)
        }
    }

    private fun loadTracks() {
        val disposable = artist.let {
            Log.v("Presenter", artist)
            trackListRepoRetrofit.getTrackList(it)
                .observeOn(uiSchedular)
                .subscribe({ artistTrackList ->
                    artistTrackList.data.let { list ->
                        trackListPresenter.tracks.addAll(list)
                        getPicture(list[0].artist.picture_medium)
                        url = list[0].artist.picture_medium
                        viewState.updateList()}
                }, {
                    Log.v("Presenter", "ошибка" + it.message)
                    print(it.message)
                })
        }
        compositeDisposable.add(disposable)
    }

    fun loadTracks(name: String) {
        viewState.setArtistName(name)
        val disposable = name.let {
            Log.v("Presenter", artist)
            trackListRepoRetrofit.getTrackList(it)
                .observeOn(uiSchedular)
                .subscribe({ artistTrackList ->
                    artistTrackList.data.let { list ->
                        trackListPresenter.tracks.clear()
                        trackListPresenter.tracks.addAll(list)
                        viewState.updatePicture(list[0].artist.picture_medium)
                        url = list[0].artist.picture_medium
                        viewState.updateList()}
                }, {
                    Log.v("Presenter", "ошибка" + it.message)
                    print(it.message)
                })
        }
        compositeDisposable.add(disposable)
        trackListPresenter.itemClickListener = {
            val track: ArtistTrack = trackListPresenter.tracks[it.pos]
            viewState.playArtistTrack(track)
        }
    }

     private fun getPicture(url: String) {
         val disposable =  url.let {
             Log.v(TAG, url)
             imageCache.getBytes(it)
                 .observeOn(uiSchedular)
                 .subscribe {
                     it?.let { byteArray ->
                         viewState.setArtistPicture(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))
                     }
                 }
         }
         compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

     fun buttonPlayClicked(song: String){
         val disposable = audioPlayer.create(song)
             .observeOn(uiScheduler)
             .subscribe({
                 audioPlayer.getDuration()
                     .subscribe({ duration ->
                         Log.v("Duration", duration.toString())
                         viewState.seekbarMax(duration)
                         audioPlayer.start().subscribe({ currentPosition ->
                             Log.v("progress", currentPosition.toString())
                             viewState.seekbarProgress(currentPosition)
                         }, { error ->
                             Log.v("Error", error.toString())
                         }, {
                             viewState.seekbarProgress(0)
                         })
                     }, {
                         viewState.seekbarMax(0)
                     })
             }, {
             })
         compositeDisposable.add(disposable)
     }

    fun buttonFavouritesClicked() {
        TODO("Not yet implemented")
    }

    fun stopClicked() {
        audioPlayer.stop()
    }


    inner class ArtistTracksListPresenter: ITrackListItemPresenter {
        val tracks = mutableListOf<ArtistTrack>()
        override var itemClickListener: ((ITrackListItemView) -> Unit)? = null

        override fun bindView(view: ITrackListItemView) {
            val track = tracks[view.pos]
            track.title.let { view.setTitle(it) }
            track.album.title.let { view.setAlbum(it) }
        }

        override fun getCount(): Int = tracks.size

    }
}