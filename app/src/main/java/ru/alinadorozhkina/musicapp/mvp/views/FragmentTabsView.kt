package ru.alinadorozhkina.musicapp.mvp.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface FragmentTabsView: MvpView {
    fun init()
}