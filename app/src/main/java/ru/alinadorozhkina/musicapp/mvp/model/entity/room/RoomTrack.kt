package ru.alinadorozhkina.musicapp.mvp.model.entity.room

import androidx.room.*
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist

@Entity
class RoomTrack(
    @PrimaryKey
    @ColumnInfo(name = "track_id")
    val id: Int,
    val title: String,
    val position: Int,
    @Embedded
    val artist: Artist
)