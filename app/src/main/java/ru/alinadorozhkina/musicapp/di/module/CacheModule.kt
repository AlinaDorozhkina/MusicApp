package ru.alinadorozhkina.musicapp.di.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.alinadorozhkina.musicapp.mvp.model.cache.ITopTracksCache
import ru.alinadorozhkina.musicapp.mvp.model.cache.ITrackListCache
import ru.alinadorozhkina.musicapp.mvp.model.cache.room.RoomTopTrackCache
import ru.alinadorozhkina.musicapp.mvp.model.cache.room.RoomTrackListCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.db.DataBase
import ru.alinadorozhkina.musicapp.ui.App

@Module
class CacheModule {
    @Provides
    fun db(app: App) = Room.databaseBuilder(app, DataBase::class.java, DataBase.DB_NAME).build()

    @Provides
    fun topTracksCache(db: DataBase): ITopTracksCache = RoomTopTrackCache(db)

    @Provides
    fun trackListCache(db: DataBase): ITrackListCache = RoomTrackListCache(db)
}