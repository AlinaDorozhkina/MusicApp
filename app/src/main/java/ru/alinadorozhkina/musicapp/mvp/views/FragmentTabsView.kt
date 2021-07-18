package ru.alinadorozhkina.musicapp.mvp.views

import android.graphics.Bitmap
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface FragmentTabsView: MvpView {
    fun init()
    fun setImageOnToolbar(decodeByteArray: Bitmap)
}