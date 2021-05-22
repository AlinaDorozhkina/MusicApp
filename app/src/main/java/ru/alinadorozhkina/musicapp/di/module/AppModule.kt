package ru.alinadorozhkina.musicapp.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import ru.alinadorozhkina.helper.Prefs
import ru.alinadorozhkina.musicapp.di.SuperScope
import ru.alinadorozhkina.musicapp.ui.App
import javax.inject.Named

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App = app

    @Named("ui-thread")
    @Provides
    fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @SuperScope
    @Provides
    fun prefs (app: App): Prefs = Prefs(app)

}