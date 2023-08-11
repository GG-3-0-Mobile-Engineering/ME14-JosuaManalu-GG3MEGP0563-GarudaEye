package com.yosha10.final_project

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.yosha10.final_project.core.utils.DarkMode
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class DisasterApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        preferences.getString(
            getString(R.string.pref_theme_key),
            getString(R.string.pref_dark_follow_system)
        )?.apply {
            val mode = DarkMode.valueOf(this.uppercase(Locale.US))
            AppCompatDelegate.setDefaultNightMode(mode.value)
        }
    }
}