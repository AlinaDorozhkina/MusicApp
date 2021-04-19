package ru.alinadorozhkina.musicapp.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArtistTrack(
    @Expose val id: Int,
    @Expose val title: String,
    @Expose val artist: Artist,
    @Expose val album: Album,
    @Expose val duration: Int,
) : Parcelable