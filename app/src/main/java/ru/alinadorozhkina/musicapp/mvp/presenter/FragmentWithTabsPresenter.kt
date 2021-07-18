package ru.alinadorozhkina.musicapp.mvp.presenter

import android.graphics.BitmapFactory
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.cache.IImageCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track
import ru.alinadorozhkina.musicapp.mvp.views.FragmentTabsView
import javax.inject.Inject
import javax.inject.Named

class FragmentWithTabsPresenter(val track: Track?):MvpPresenter<FragmentTabsView>() {

    val compositeDisposable = CompositeDisposable()
    @field:Named("ui-thread") @Inject
    lateinit var uiScheduler: Scheduler
    @Inject lateinit var imageCache: IImageCache

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        track?.artist?.picture_medium?.let { getPicture(it) }
    }

    private fun getPicture(url: String) {
        val disposable =  url.let {
            imageCache.getBytes(it)
                .observeOn(uiScheduler)
                .subscribe {
                    it?.let { byteArray ->
                        viewState.setImageOnToolbar(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))
                    }
                }
        }
        compositeDisposable.add(disposable)
    }
    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}