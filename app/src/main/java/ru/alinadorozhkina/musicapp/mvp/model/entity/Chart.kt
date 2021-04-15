package ru.alinadorozhkina.musicapp.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chart (
    @Expose val data: List<Track>,
    @Expose val total: Int
        ): Parcelable
