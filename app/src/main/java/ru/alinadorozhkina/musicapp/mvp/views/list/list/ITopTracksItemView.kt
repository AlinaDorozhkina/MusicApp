package ru.alinadorozhkina.musicapp.mvp.views.list.list

interface ITopTracksItemView : IItemView {
    fun init ()
    fun setTitle(title: String)
    fun setPosition(position: Int)
    fun setArtist(artist: String)
    fun loadPoster(url: String)
    fun seekbarMax(duration: Int)
    fun seekbarProgress(progress: Int)
}