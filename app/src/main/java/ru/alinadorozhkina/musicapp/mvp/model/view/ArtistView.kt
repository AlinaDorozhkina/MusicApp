package ru.alinadorozhkina.musicapp.mvp.model.view

import android.graphics.Bitmap
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ArtistView : MvpView {
    fun setArtistName(name: String)
    fun setArtistPicture(bitmap: Bitmap)
    fun init()
    fun updateList()
}
