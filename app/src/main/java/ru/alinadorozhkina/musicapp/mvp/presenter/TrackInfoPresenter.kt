package ru.alinadorozhkina.musicapp.mvp.presenter

import moxy.MvpPresenter
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track
import ru.alinadorozhkina.musicapp.mvp.views.TrackInfoView

class TrackInfoPresenter(track: Track): MvpPresenter<TrackInfoView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }


}