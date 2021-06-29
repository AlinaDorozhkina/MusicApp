package ru.alinadorozhkina.musicapp.mvp.views.list

import android.graphics.Bitmap
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchView: MvpView {
    fun setArtistName(name: String?)
    fun setArtistPicture(bitmap: Bitmap)
    fun init()
    fun updateList()
}