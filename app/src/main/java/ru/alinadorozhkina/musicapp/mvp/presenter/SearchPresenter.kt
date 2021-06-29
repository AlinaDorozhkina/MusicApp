package ru.alinadorozhkina.musicapp.mvp.presenter

import android.util.Log
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrack
import ru.alinadorozhkina.musicapp.mvp.model.repo.ITrackListRepo
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITrackListItemPresenter
import ru.alinadorozhkina.musicapp.mvp.views.list.SearchView
import ru.alinadorozhkina.musicapp.mvp.views.list.list.ITrackListItemView
import javax.inject.Inject
import javax.inject.Named

class SearchPresenter(val name: String?) : MvpPresenter<SearchView>() {

    val trackListPresenter = SearchArtistTracksListPresenter()
    val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var trackListRepoRetrofit: ITrackListRepo
    @field:Named("ui-thread")
    @Inject
    lateinit var uiSchedular: Scheduler

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        name.let {
            viewState.setArtistName(it)
        }
        viewState.init()

        loadTracks()

    }

    private fun loadTracks() {
        Log.v("SearchPresenter", "loadTracks()")
        val disposable = name.let {
            trackListRepoRetrofit.getTrackList(it)
                .observeOn(uiSchedular)
                .subscribe({ artistTrackList ->
                    Log.v("SearchPresenter", artistTrackList.toString())
                    artistTrackList.data.let { list ->
                        Log.v("SearchPresenter", list.toString())
                        trackListPresenter.tracks.addAll(list)
                        viewState.updateList()
                    }
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

class SearchArtistTracksListPresenter : ITrackListItemPresenter {
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