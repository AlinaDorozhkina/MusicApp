package ru.alinadorozhkina.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class Prefs(context: Context) {
    companion object {
        private const val PREFS_FILENAME = "shared_prefs_name"
        private const val THEME_COLOR = "color"
    }

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var themeColor: String
        get() = sharedPrefs.getString(THEME_COLOR, "") ?: "pink"
        set(value) = sharedPrefs.edit {
            putString(THEME_COLOR, value).apply()
        }
}