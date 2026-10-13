package com.ven.app.data.preference

import android.content.Context
import android.content.SharedPreferences
import com.ven.app.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Pengelola preferensi tema aplikasi (Light / Dark / System).
 * Menggunakan SharedPreferences dengan StateFlow reaktif.
 */
class ThemePreferences private constructor(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val _themeMode = MutableStateFlow(loadThemeMode())
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private fun loadThemeMode(): ThemeMode {
        val saved = preferences.getString(KEY_THEME_MODE, ThemeMode.Dark.name)
        return try {
            ThemeMode.valueOf(saved ?: ThemeMode.Dark.name)
        } catch (e: Exception) {
            ThemeMode.Dark
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        preferences.edit().putString(KEY_THEME_MODE, mode.name).apply()
        _themeMode.value = mode
    }

    companion object {
        private const val PREF_NAME = "ven_theme_pref"
        private const val KEY_THEME_MODE = "key_theme_mode"

        @Volatile
        private var INSTANCE: ThemePreferences? = null

        fun getInstance(context: Context): ThemePreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ThemePreferences(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
