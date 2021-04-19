package ru.alinadorozhkina.musicapp.mvp.model.view.list

interface ITopTracksItemView : IItemView {
    fun setTitle(title: String)
    fun setPosition(position: Int)
    fun setArtist(artist: String)
    fun loadPoster(url: String)
}