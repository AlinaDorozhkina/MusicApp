package ru.alinadorozhkina.musicapp.mvp.model.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TopTrackView: MvpView {
    fun init()
    fun updateList()
}