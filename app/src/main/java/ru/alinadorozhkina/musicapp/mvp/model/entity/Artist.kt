package ru.alinadorozhkina.musicapp.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist(
    @Expose val id: Int,
    @Expose var name: String,
    @Expose val picture: String,
    @Expose val tracklist: String
) : Parcelable