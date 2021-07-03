package ru.alinadorozhkina.musicapp.mvp.model.entity.room

import androidx.room.*
import com.google.gson.annotations.Expose
import ru.alinadorozhkina.musicapp.mvp.model.entity.Album
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist

@Entity
class RoomTrack(
    @PrimaryKey
    @ColumnInfo(name = "track_id")
    val id: Int,
    @ColumnInfo(name = "track_title")
    val title: String,
    val position: Int,
    val title_short: String,
    val explicit_lyrics: Boolean,
    @Embedded
    val artist: Artist,
    @Embedded
    val album: Album,
    val preview: String
)