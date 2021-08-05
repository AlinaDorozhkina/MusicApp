package ru.alinadorozhkina.musicapp.mvp.views.list.list

interface ITracksByGenreItemView: IItemView {
    fun init()
    fun loadPicture(url: String)
    fun setTGenre(genre: String)
}