package ru.alinadorozhkina.musicapp.mvp.model.entity.room

import androidx.room.Embedded
import androidx.room.Relation

//class ArtistWithPlaylist(
//    @Embedded val artist: RoomArtist,
//    @Relation(
//        parentColumn = "roomArtistId",
//        entityColumn = "artistId"
//    )
//    val tracklist: List<RoomArtistTrack>
//)