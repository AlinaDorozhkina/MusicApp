package ru.alinadorozhkina.musicapp.mvp.model.entity.room

import androidx.room.*
import ru.alinadorozhkina.musicapp.mvp.model.entity.Album
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomArtist::class,
        parentColumns = ["artist_id"],
        childColumns = ["artistId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomArtistTrack(
    @PrimaryKey
    @ColumnInfo(name = "room_artist_id") val id: Int,
    @ColumnInfo(name = "track_title")
    val title: String,
    @Embedded
    val album: Album,
    val duration: Int,
    var artistId: String,
    var preview: String
)