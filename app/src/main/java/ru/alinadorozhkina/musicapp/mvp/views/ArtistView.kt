package ru.alinadorozhkina.musicapp.mvp.views

import android.graphics.Bitmap
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrack

@StateStrategyType(AddToEndSingleStrategy::class)
interface ArtistView : MvpView {
    fun setArtistName(name: String)
    fun setArtistPicture(bitmap: Bitmap)
    fun init()
    fun updateList()
    fun playArtistTrack(track: ArtistTrack)
    fun seekbarMax(duration: Int)
    fun seekbarProgress(progress: Int)
}
