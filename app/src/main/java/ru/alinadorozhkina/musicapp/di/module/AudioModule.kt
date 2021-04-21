package ru.alinadorozhkina.musicapp.di.module

import android.media.MediaPlayer
import dagger.Module
import dagger.Provides
import ru.alinadorozhkina.musicapp.di.SuperScope
import ru.alinadorozhkina.musicapp.mvp.model.audio.IAudioPlayer
import ru.alinadorozhkina.musicapp.ui.App
import ru.alinadorozhkina.musicapp.ui.audio.AndroidMediaPlayer

@Module
class AudioModule {
    @SuperScope
    @Provides
    fun audio(app: App): IAudioPlayer = AndroidMediaPlayer(app)
    @SuperScope
    @Provides
    fun player(): MediaPlayer = MediaPlayer()
}