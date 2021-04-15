package ru.alinadorozhkina.musicapp.mvp.model.entity.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.*
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.dao.*

@androidx.room.Database(
    entities = [
        RoomTrack::class,
    RoomArtistTrack::class,
    RoomArtist::class
    ],
    version = 2,
    exportSchema = true
)
abstract class DataBase: RoomDatabase() {
    abstract val chartTracksDao: ChartTrackDao
    abstract val artistTrackListDao: ArtistTrackListDao
    abstract val artistDao: ArtistDao

    companion object{
        private const val DB_NAME = "data_base2.db"
        private var instance: DataBase? = null
        fun create (context: Context){
            if (instance == null){
                instance = Room.databaseBuilder(context, DataBase::class.java, DB_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
        }
        fun getInstance() = instance ?: throw RuntimeException("Database has not been created. Please call create(context)")
    }

}