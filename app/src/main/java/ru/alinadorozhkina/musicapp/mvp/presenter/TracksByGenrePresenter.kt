package ru.alinadorozhkina.musicapp.mvp.presenter

import android.util.Log
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.entity.Type
import ru.alinadorozhkina.musicapp.mvp.model.repo.ITracksByGenreRepo
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITracksByGenreListPresenter
import ru.alinadorozhkina.musicapp.mvp.views.TracksByGenreView
import ru.alinadorozhkina.musicapp.mvp.views.list.list.ITracksByGenreItemView
import javax.inject.Inject
import javax.inject.Named

class TracksByGenrePresenter: MvpPresenter<TracksByGenreView>() {
    @field:Named("ui-thread")
    @Inject
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var genreRepoRetrofit: ITracksByGenreRepo

    val compositeDisposable = CompositeDisposable()
    val tracksByGenreListPresenter = TracksByGenreListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
    }

    private fun loadData(){
        val disposable = genreRepoRetrofit.getTracksByGenre()
            .observeOn(uiScheduler)
            .subscribe({
                Log.v("TAG", it.toString())
                it.data.let { listOfTypes ->
                    Log.v("TAG", listOfTypes.toString())
                    tracksByGenreListPresenter.types.addAll(listOfTypes)
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
        super.onDestroy()
    }




    inner class TracksByGenreListPresenter: ITracksByGenreListPresenter{
        val types = mutableListOf<Type>()
        override var itemClickListener: ((ITracksByGenreItemView) -> Unit)? = null


        override fun bindView(view: ITracksByGenreItemView) {
            val type = types[view.pos]
           // view.init()
            view.setTGenre(type.title)
            view.loadPicture(type.picture_small)

        }

        override fun getCount(): Int = types.size

    }

}