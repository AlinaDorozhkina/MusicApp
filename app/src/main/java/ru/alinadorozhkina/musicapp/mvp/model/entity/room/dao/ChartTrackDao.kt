package ru.alinadorozhkina.musicapp.mvp.model.entity.room.dao

import androidx.room.*
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.RoomTrack

@Dao
interface ChartTrackDao {

    @Query("SELECT * FROM RoomTrack")
    fun getChartTracks(): List<RoomTrack>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tracks: List<RoomTrack>)

    @Update
    fun update(tracks: List<RoomTrack>)

}