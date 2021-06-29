package ru.alinadorozhkina.musicapp.mvp.presenter

import android.graphics.BitmapFactory
import android.util.Log
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.cache.IImageCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrack
import ru.alinadorozhkina.musicapp.mvp.model.repo.ITrackListRepo
import ru.alinadorozhkina.musicapp.mvp.views.ArtistView
import ru.alinadorozhkina.musicapp.mvp.views.list.list.ITrackListItemView
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITrackListItemPresenter
import javax.inject.Inject
import javax.inject.Named

class ArtistPresenter(val artist: Artist) : MvpPresenter<ArtistView>() {

    @field:Named("ui-thread") @Inject lateinit var uiScheduler: Scheduler
    @Inject lateinit var imageCache: IImageCache
    @Inject lateinit var screens: IScreens
    @Inject lateinit var router: Router
    @field:Named("ui-thread")@Inject lateinit var uiSchedular: Scheduler
    @Inject lateinit var trackListRepoRetrofit: ITrackListRepo

    val trackListPresenter = ArtistTracksListPresenter()
    val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        artist.let {
            viewState.setArtistName(it.name)
        }
        viewState.init()
        loadTracks()

        imageCache.getBytes(artist.picture).observeOn(uiScheduler)
            .subscribe {
                it?.let { byteArray ->
                    val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    viewState.setArtistPicture(bmp)
                }
            }

    }

    private fun loadTracks() {
        val disposable = artist.let {
            Log.v("Presenter", artist.toString())
            trackListRepoRetrofit.getTrackList(it)
                .observeOn(uiSchedular)
                .subscribe({ artistTrackList ->
                    artistTrackList.data.let { list ->
                        trackListPresenter.tracks.addAll(list)
                        viewState.updateList()}
                }, {
                    Log.v("Presenter", "ошибка" + it.message)
                    print(it.message)
                })
        }
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }


    class ArtistTracksListPresenter: ITrackListItemPresenter {
        val tracks = mutableListOf<ArtistTrack>()
        override var itemClickListener: ((ITrackListItemView) -> Unit)? = null

        override fun bindView(view: ITrackListItemView) {
            val track = tracks[view.pos]
            track.title.let { view.setTitle(it) }
            track.album.title.let { view.setAlbum(it) }
            track.album.cover.let { view.loadCover(it) }
        }

        override fun getCount(): Int = tracks.size

    }

}