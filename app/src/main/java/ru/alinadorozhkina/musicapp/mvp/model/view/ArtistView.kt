package ru.alinadorozhkina.musicapp.mvp.model.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ArtistView: MvpView {
    fun setArtistName(name: String)
    fun setArtistPicture()
}