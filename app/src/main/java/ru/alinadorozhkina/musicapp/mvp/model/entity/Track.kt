package ru.alinadorozhkina.musicapp.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Track(
    @Expose val id: Int,
    @Expose val title: String,
    @Expose val position: Int,
    @Expose val artist: Artist,
    @Expose val album: Album,
    @Expose val preview: String,
    @Expose val title_short: String,
    @Expose val explicit_lyrics: Boolean
) : Parcelable



