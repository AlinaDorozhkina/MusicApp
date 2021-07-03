package ru.alinadorozhkina.musicapp.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import retrofit2.http.Url
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrack
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrackList
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track

interface ITrackListRepo {
    fun getTrackList(artist: Artist): Single<ArtistTrackList>
    fun getTrackList(artist: String?): Single<ArtistTrackList>
}