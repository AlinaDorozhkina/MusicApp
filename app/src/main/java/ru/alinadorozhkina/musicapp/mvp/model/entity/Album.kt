package ru.alinadorozhkina.musicapp.mvp.model.entity

import android.os.Parcelable
import androidx.room.Ignore
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album(
    @Expose val title: String,
    @Expose val cover: String,
) : Parcelable