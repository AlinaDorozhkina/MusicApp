package ru.alinadorozhkina.musicapp.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.alinadorozhkina.musicapp.api.IDataSource
import ru.alinadorozhkina.musicapp.mvp.model.network.INetworkStatus
import ru.alinadorozhkina.musicapp.ui.App
import ru.alinadorozhkina.musicapp.ui.network.AndroidNetworkStatus

@Module
class ApiModule {

    @Provides
    fun api(gson: Gson): IDataSource = Retrofit.Builder()
        .baseUrl("https://api.deezer.com")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(IDataSource::class.java)

    @Provides
    fun gson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Provides
    fun networkStatus(app: App): INetworkStatus = AndroidNetworkStatus(app)

}