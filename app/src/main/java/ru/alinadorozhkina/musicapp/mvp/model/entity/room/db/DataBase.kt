package ru.alinadorozhkina.musicapp.mvp.model.entity.room.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.*
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.dao.ArtistDao
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.dao.ArtistTrackListDao
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.dao.ChartTrackDao
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.dao.ImageDao

@androidx.room.Database(
    entities = [
        RoomTrack::class,
        RoomArtistTrack::class,
        RoomArtist::class,
        RoomCachedImage::class
    ],
    version = 5,
    exportSchema = true
)
abstract class DataBase : RoomDatabase() {
    abstract val chartTracksDao: ChartTrackDao
    abstract val artistTrackListDao: ArtistTrackListDao
    abstract val artistDao: ArtistDao
    abstract val imageDao: ImageDao

    companion object {
        const val DB_NAME = "data_base5.db"
        private var instance: DataBase? = null
        fun create(context: Context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, DataBase::class.java, DB_NAME)
                    .build()
            }
        }

        fun getInstance() = instance
            ?: throw RuntimeException("Database has not been created. Please call create(context)")
    }

}