package ru.alinadorozhkina.musicapp.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrack
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrackList

interface ITrackListCache {
    fun getArtistTrackList(artist: Artist): Single<ArtistTrackList>
    fun putArtistTrackList(artist: Artist, artistTrackList: ArtistTrackList): Completable
    fun putArtist(artist: Artist): Completable
}