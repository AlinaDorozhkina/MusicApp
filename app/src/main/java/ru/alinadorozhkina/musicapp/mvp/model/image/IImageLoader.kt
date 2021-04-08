package ru.alinadorozhkina.musicapp.mvp.model.image

interface IImageLoader <T> {
    fun load (url: String, container: T)
}