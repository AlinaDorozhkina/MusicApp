package ru.alinadorozhkina.musicapp.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrackList
import ru.alinadorozhkina.musicapp.mvp.model.entity.Chart

interface IDataSource {

    @GET("/chart/0/tracks")
    fun getTopTrack(): Single<Chart>

    @GET
    fun getTrackList(@Url url: String): Single<ArtistTrackList>

    @GET("/search")
    fun getArtistTracks(@Query("q") name: String?): Single<ArtistTrackList>

}