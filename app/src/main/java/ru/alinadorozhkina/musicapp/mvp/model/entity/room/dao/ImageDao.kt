package ru.alinadorozhkina.musicapp.mvp.model.entity.room.dao

import androidx.room.*
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.RoomCachedImage

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: RoomCachedImage)

    @Update
    fun update(image: RoomCachedImage)

    @Delete
    fun delete(image: RoomCachedImage)

    @Query("DELETE FROM RoomCachedImage")
    fun clear()

    @Query("SELECT * FROM RoomCachedImage WHERE url = :url LIMIT 1")
    fun findByUrl(url: String): RoomCachedImage?

}