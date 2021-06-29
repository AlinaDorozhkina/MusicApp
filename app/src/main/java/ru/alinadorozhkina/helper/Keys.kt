package ru.alinadorozhkina.helper

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue
import ru.alinadorozhkina.musicapp.R


var SHARED_THEME = "pink"

fun getColorRes(context: Context, position: Int): Int = when (position) {
    1 -> context.resources.getColor(R.color.gold)
    2 -> context.resources.getColor(R.color.silver)
    3 -> context.resources.getColor(R.color.bronze)
    else -> context.resources.getColor(R.color.grey_dark)
}



interface BackButtonListener {
    fun backPressed(): Boolean
}

