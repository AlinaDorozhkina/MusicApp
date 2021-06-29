package ru.alinadorozhkina.musicapp.mvp.presenter

import android.util.Log
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.audio.IAudioPlayer
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track
import ru.alinadorozhkina.musicapp.mvp.model.repo.ITopTracksRepo
import ru.alinadorozhkina.musicapp.mvp.views.TopTrackView
import ru.alinadorozhkina.musicapp.mvp.views.list.list.ITopTracksItemView
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITopTracksListPresenter
import javax.inject.Inject
import javax.inject.Named

class TopTrackPresenter : MvpPresenter<TopTrackView>() {

    @field:Named("ui-thread")
    @Inject lateinit var uiScheduler: Scheduler

    @Inject lateinit var topTracksRepoRetrofit: ITopTracksRepo

    @Inject lateinit var screens: IScreens

    @Inject lateinit var router: Router

    @Inject lateinit var audioPlayer: IAudioPlayer<Int>

    val compositeDisposable = CompositeDisposable()

    inner class TopTracksListPresenter : ITopTracksListPresenter {
        val tracks = mutableListOf<Track>()
        override var itemClickListener: ((ITopTracksItemView) -> Unit)? = null

        override fun bindView(view: ITopTracksItemView) {
            val track = tracks[view.pos]
            view.init()
            Log.v("TAG", track.toString() )
            track.title.let { view.setTitle(it) }
            track.artist.let { it.name.let { name -> view.setArtist(name) } }
            track.position.let { view.setPosition(it) }
            track.artist.picture.let { view.loadPoster(it) }
        }

        override fun getCount(): Int = tracks.size
        override fun playClicked(position: Int, view: ITopTracksItemView) {
            val song = tracks[position].preview

            val disposable = audioPlayer.create(song)
                .observeOn(uiScheduler)
                .subscribe({
                    audioPlayer.getDuration()
                        .subscribe({ duration ->
                            Log.v("Duration", duration.toString())
                            view.seekbarMax(duration)
                            audioPlayer.start().subscribe({ currentPosition ->
                                Log.v("progress", currentPosition.toString())
                                view.seekbarProgress(currentPosition)
                            }, { error ->
                                Log.v("Error", error.toString())
                                // показать через холдер тост об ошибке
                            }, {
                                view.seekbarProgress(0)
                            })
                        }, {
                            view.seekbarMax(0)
                        })
                }, {
                })
            compositeDisposable.add(disposable)
        }

        override fun stopClicked() {
            audioPlayer.stop()
        }

        override fun favouritesClicked(position: Int) {
            val favourites = tracks[position]
            Log.v("Favourites", favourites.toString())
        }
    }

    val topTrackListPresenter = TopTracksListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadTracks()

        topTrackListPresenter.itemClickListener = { itemView ->
            val artist: Artist = topTrackListPresenter.tracks[itemView.pos].artist
            router.navigateTo(screens.artist(artist))
        }
    }

    private fun loadTracks() {
        val disposable = topTracksRepoRetrofit.getTopTracks()
            .observeOn(uiScheduler)
            .subscribe({
                it.data.let { it1 ->
                    topTrackListPresenter.tracks.addAll(it1)
                    viewState.setTopTrackAmount(it.total)
                    viewState.updateList()
                }
            }, {
                Log.v("Presenter loadTracks", "ошибка" + it.message)
                print(it.message)
            })
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        audioPlayer.clear()
        super.onDestroy()
    }

    fun favouritesClicked() {
        TODO("Not yet implemented")
    }

    fun settingsClicked() {
        router.navigateTo(screens.settings())
    }


    fun search(s: String) {
        router.navigateTo(screens.search(s))

    }

}