package ru.alinadorozhkina.musicapp.mvp.views.list.list

interface ITrackListItemView : IItemView {
    fun setTitle(text: String)
    fun setAlbum(title: String)
}