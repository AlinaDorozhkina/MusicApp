package ru.alinadorozhkina.musicapp.mvp.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TracksByGenreView: MvpView {
    fun init()
    fun updateList()
}