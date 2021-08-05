package ru.alinadorozhkina.musicapp.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Type(
    @Expose val title: String,
    @Expose val picture_small: String,
    @Expose val tracklist: String
) : Parcelable