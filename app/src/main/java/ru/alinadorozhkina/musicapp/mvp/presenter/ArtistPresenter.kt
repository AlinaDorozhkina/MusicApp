package ru.alinadorozhkina.musicapp.mvp.presenter

import android.graphics.BitmapFactory
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.cache.IImageCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.view.ArtistView
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens

class ArtistPresenter(
    val artist: Artist,
    val router: Router,
    val screens: IScreens,
    val imageCache: IImageCache
) : MvpPresenter<ArtistView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        artist.let {
            viewState.setArtistName(it.name)
        }

        imageCache.getBytes(artist.picture).subscribe {
            it?.let { byteArray ->
                val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                viewState.setArtistPicture(bmp)
            }
        }
    }

    fun buttonTrackListClicked() {
        router.navigateTo(screens.artistTracks(artist))
    }


}