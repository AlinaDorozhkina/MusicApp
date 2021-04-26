package ru.alinadorozhkina.musicapp.mvp.model.view.list

interface ITrackListItemView : IItemView {
    fun setTitle(text: String)
    fun setAlbum(title: String)
    fun loadCover(url: String)
}