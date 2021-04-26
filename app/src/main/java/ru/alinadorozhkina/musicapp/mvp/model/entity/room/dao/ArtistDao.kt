package ru.alinadorozhkina.musicapp.mvp.model.entity.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.RoomArtist

@Dao
interface ArtistDao {

    @Query("SELECT * FROM RoomArtist WHERE name = :name LIMIT 1")
    fun findByName(name: String): RoomArtist

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(artist: RoomArtist)

}