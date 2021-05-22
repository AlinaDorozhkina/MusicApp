//package ru.alinadorozhkina.musicapp.mvp.presenter
//
//import android.util.Log
//import io.reactivex.rxjava3.core.Scheduler
//import io.reactivex.rxjava3.disposables.CompositeDisposable
//import moxy.MvpPresenter
//import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrack
//import ru.alinadorozhkina.musicapp.mvp.model.repo.ITrackListRepo
//import ru.alinadorozhkina.musicapp.mvp.model.view.ArtistView
//import ru.alinadorozhkina.musicapp.mvp.model.view.ArtistView2
//import ru.alinadorozhkina.musicapp.mvp.model.view.list.ITrackListItemView
//import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITrackListItemPresenter
//import javax.inject.Inject
//import javax.inject.Named
//
//class ArtistInfoPresenter(artist: String): MvpPresenter<ArtistView2>() {
//    @field:Named("ui-thread")@Inject
//    lateinit var uiSchedular: Scheduler
//    @Inject
//    lateinit var trackListRepoRetrofit: ITrackListRepo
//
//    class ArtistTracksListPresenter: ITrackListItemPresenter {
//        val tracks = mutableListOf<ArtistTrack>()
//        override var itemClickListener: ((ITrackListItemView) -> Unit)? = null
//
//        override fun bindView(view: ITrackListItemView) {
//            val track = tracks[view.pos]
//            track.title.let { view.setTitle(it) }
//            track.album.title.let { view.setAlbum(it) }
//            track.album.cover.let { view.loadCover(it) }
//        }
//
//        override fun getCount(): Int = tracks.size
//
//    }
//
//    val trackListPresenter = ArtistTracksListPresenter()
//    val compositeDisposable = CompositeDisposable()
//
//    override fun onFirstViewAttach() {
//        super.onFirstViewAttach()
//        viewState.init()
//        loadTracks()
//    }
//
//    private fun loadTracks() {
//        val disposable = artist.let {
//            Log.v("Presenter", artist.toString())
//            trackListRepoRetrofit.getTrackList(it)
//                .observeOn(uiSchedular)
//                .subscribe({ artistTrackList ->
//                    artistTrackList.data.let { list ->
//                        trackListPresenter.tracks.addAll(list)
//                        viewState.updateList()}
//                }, {
//                    Log.v("Presenter", "ошибка" + it.message)
//                    print(it.message)
//                })
//        }
//        compositeDisposable.add(disposable)
//    }
//
//    override fun onDestroy() {
//        compositeDisposable.dispose()
//        super.onDestroy()
//    }
//}