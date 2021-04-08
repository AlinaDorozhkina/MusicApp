package ru.alinadorozhkina.musicapp.mvp.presenter

import android.util.Log
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track
import ru.alinadorozhkina.musicapp.mvp.model.repo.ITopTracksRepo
import ru.alinadorozhkina.musicapp.mvp.model.view.TopTrackView
import ru.alinadorozhkina.musicapp.mvp.model.view.list.ITopTracksItemView
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITopTracksListPresenter

class TopTrackPresenter(
    val topTracksRepoRetrofit: ITopTracksRepo,
    val uiSchedular: Scheduler,
    val router: Router,
    val screens: IScreens
) : MvpPresenter<TopTrackView>() {

    class TopTracksListPresenter : ITopTracksListPresenter {
        val tracks = mutableListOf<Track>()
        override var itemClickListener: ((ITopTracksItemView) -> Unit)? = null

        override fun bindView(view: ITopTracksItemView) {
            val track = tracks[view.pos]
            track.title.let { view.setTitle(it) }
            track.artist.let { it.name.let { name -> view.setArtist(name) } }
            track.position.let { view.setPosition(it) }
            track.artist.picture.let { view.loadPoster(it) }
        }

        override fun getCount(): Int = tracks.size
    }

    val topTrackListPresenter = TopTracksListPresenter()
    val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadTracks()

        topTrackListPresenter.itemClickListener = { itemView ->
            val artist: Artist = topTrackListPresenter.tracks[itemView.pos].artist
            Log.v("TAG", artist.toString())
            router.navigateTo(screens.artist(artist))
        }
    }

    private fun loadTracks() {
        val disposable = topTracksRepoRetrofit.getTopTracks()
            .observeOn(uiSchedular)
            .subscribe({
                it.data?.let { it1 -> topTrackListPresenter.tracks.addAll(it1)
                viewState.updateList()}
            }, {
                Log.v("Presenter", "ошибка" + it.message)
                print(it.message)
            })
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}