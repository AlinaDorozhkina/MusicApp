package ru.alinadorozhkina.helper

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.TypedValue
import ru.alinadorozhkina.musicapp.R


//fun getColorRes(context: Context, position: Int): Int = when (position) {
//    1 -> context.resources.getDrawable(R.drawable.gold_background)
//    2 -> context.resources.getColor(R.color.silver)
//    3 -> context.resources.getColor(R.color.bronze)
//    else -> context.resources.getColor(R.color.grey_dark)
//}

fun getColorRes(context: Context, position: Int): Drawable = when (position) {
    1 -> context.resources.getDrawable(R.drawable.gold_background)
    2 -> context.resources.getDrawable(R.drawable.silver_background)
    3 -> context.resources.getDrawable(R.drawable.bronze_background)
    else -> context.resources.getDrawable(R.drawable.chart_all_background)
}



interface BackButtonListener {
    fun backPressed(): Boolean
}

