package ru.alinadorozhkina.musicapp.mvp.model.entity.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity
class RoomArtist(
    @PrimaryKey
    @ColumnInfo(name = "artist_id") val id: Int,
    @Expose var name: String,
    @Expose val picture: String,
    @Expose val tracklist: String

)