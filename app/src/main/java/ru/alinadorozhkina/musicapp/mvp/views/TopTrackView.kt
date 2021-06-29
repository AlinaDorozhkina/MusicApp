package ru.alinadorozhkina.musicapp.mvp.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TopTrackView : MvpView {
    fun setTopTrackAmount(total: Int)
    fun init()
    fun updateList()
}