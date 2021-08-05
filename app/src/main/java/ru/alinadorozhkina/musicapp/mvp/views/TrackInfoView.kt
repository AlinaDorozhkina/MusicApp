package ru.alinadorozhkina.musicapp.mvp.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TrackInfoView: MvpView{
    fun setTrackTitle(title: String)
    fun setArtistName(name: String)
    fun setAlbumTitle(album: String)
}