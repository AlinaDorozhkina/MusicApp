package ru.alinadorozhkina.musicapp.di.module

import dagger.Module
import dagger.Provides
import ru.alinadorozhkina.musicapp.api.IDataSource
import ru.alinadorozhkina.musicapp.di.SuperScope
import ru.alinadorozhkina.musicapp.mvp.model.repo.ITracksByGenreRepo
import ru.alinadorozhkina.musicapp.mvp.model.repo.RetrofitTracksByGenreRepo

@Module
class GenreModule {
    @SuperScope
    @Provides
    fun tracksByGenre(api: IDataSource): ITracksByGenreRepo = RetrofitTracksByGenreRepo(api)
}