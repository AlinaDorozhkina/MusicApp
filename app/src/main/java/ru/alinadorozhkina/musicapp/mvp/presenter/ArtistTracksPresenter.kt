package ru.alinadorozhkina.musicapp.mvp.presenter

import android.util.Log
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrack
import ru.alinadorozhkina.musicapp.mvp.model.repo.ITrackListRepo
import ru.alinadorozhkina.musicapp.mvp.model.view.TrackLisView
import ru.alinadorozhkina.musicapp.mvp.model.view.list.ITrackListItemView
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITrackListItemPresenter

class ArtistTracksPresenter(
    val trackListRepoRetrofit: ITrackListRepo,
    val uiSchedular: Scheduler,
    val url: String?,
): MvpPresenter<TrackLisView>() {

    class ArtistTracksListPresenter: ITrackListItemPresenter{
        val tracks = mutableListOf<ArtistTrack>()
        override var itemClickListener: ((ITrackListItemView) -> Unit)?=null

        override fun bindView(view: ITrackListItemView) {
            val track = tracks[view.pos]
            track.title.let { view.setTitle(it) }
            track.album.title.let { view.setAlbum(it) }
            track.album.cover.let { view.loadCover(it) }
        }

        override fun getCount(): Int = tracks.size
    }

    val trackListPresenter = ArtistTracksListPresenter()
    val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadTracks()
    }

    private fun loadTracks() {
        val disposable = url?.let {
            trackListRepoRetrofit.getTrackList(it)
                .observeOn(uiSchedular)
                .subscribe({
                    it.data.let { it1 -> trackListPresenter.tracks.addAll(it1)
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
}