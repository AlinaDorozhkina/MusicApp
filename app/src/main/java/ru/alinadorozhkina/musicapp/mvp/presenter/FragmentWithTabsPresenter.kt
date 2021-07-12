package ru.alinadorozhkina.musicapp.mvp.presenter

import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.views.FragmentTabsView

class FragmentWithTabsPresenter:MvpPresenter<FragmentTabsView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }
}