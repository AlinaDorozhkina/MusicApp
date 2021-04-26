package ru.alinadorozhkina.musicapp.mvp.model.entity.room.dao

import androidx.room.*
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.RoomArtistTrack

@Dao
interface ArtistTrackListDao {

    @Transaction
    @Query("SELECT * FROM RoomArtistTrack")
    fun getArtistTracks(): List<RoomArtistTrack>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tracks: List<RoomArtistTrack>)

    @Update
    fun update(tracks: List<RoomArtistTrack>)

    @Query("SELECT * FROM RoomArtistTrack WHERE artistId = :artistId")
    fun findForArtist(artistId: String): List<RoomArtistTrack>

}