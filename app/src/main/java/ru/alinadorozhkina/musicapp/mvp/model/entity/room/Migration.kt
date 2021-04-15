package ru.alinadorozhkina.musicapp.mvp.model.entity.room

import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `Artist` (`artist_id` INTEGER, `name` TEXT, `picture` TEXT, `tracklist` TEXT, PRIMARY KEY(artist_id))")
    }
}