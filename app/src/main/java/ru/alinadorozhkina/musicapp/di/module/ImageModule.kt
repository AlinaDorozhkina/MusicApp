package ru.alinadorozhkina.musicapp.di.module

import android.widget.ImageView
import dagger.Module
import dagger.Provides
import ru.alinadorozhkina.musicapp.di.SuperScope
import ru.alinadorozhkina.musicapp.mvp.model.cache.IImageCache
import ru.alinadorozhkina.musicapp.mvp.model.cache.room.RoomImageCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.db.DataBase
import ru.alinadorozhkina.musicapp.mvp.model.image.IImageLoader
import ru.alinadorozhkina.musicapp.mvp.model.network.INetworkStatus
import ru.alinadorozhkina.musicapp.ui.App
import ru.alinadorozhkina.musicapp.ui.image.GlideImageLoader
import java.io.File
import javax.inject.Named

@Module
class ImageModule {

    @Named("cacheDir")
    @SuperScope
    @Provides
    fun cacheDir(app: App): File = app.cacheDir

    @SuperScope
    @Provides
    fun imageCache(database: DataBase, @Named("cacheDir") cacheDir: File): IImageCache =
        RoomImageCache(database, cacheDir)

    @SuperScope
    @Provides
    fun imageLoader(cache: IImageCache, networkStatus: INetworkStatus): IImageLoader<ImageView> =
        GlideImageLoader(cache, networkStatus)

}