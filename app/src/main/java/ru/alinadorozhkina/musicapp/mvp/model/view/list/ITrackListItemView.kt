package ru.alinadorozhkina.musicapp.mvp.model.view.list

import ru.alinadorozhkina.musicapp.mvp.model.entity.Album

interface ITrackListItemView: IItemView {
    fun setTitle(text: String)
    fun setAlbum(title: String)
    fun loadCover(url: String)
}