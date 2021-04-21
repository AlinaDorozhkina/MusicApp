package ru.alinadorozhkina.musicapp.mvp.model.view.list

import android.net.Uri

interface ITopTracksItemView : IItemView {
    fun setTitle(title: String)
    fun setPosition(position: Int)
    fun setArtist(artist: String)
    fun loadPoster(url: String)
   // fun play(uri: Uri)
}