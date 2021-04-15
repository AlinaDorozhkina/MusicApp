package ru.alinadorozhkina.musicapp.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.view.ArtistView
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens

class ArtistPresenter(val artist: Artist, val router: Router, val screens: IScreens): MvpPresenter<ArtistView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        artist.let { viewState.setArtistName(it.name) }
    }

    fun buttonTrackListClicked(){
        router.navigateTo(screens.artistTracks(artist))
    }
}