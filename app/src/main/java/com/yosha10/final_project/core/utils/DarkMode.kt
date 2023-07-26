package com.yosha10.final_project.core.utils

import androidx.appcompat.app.AppCompatDelegate

enum class DarkMode(val value: Int) {

    FOLLOW_SYSTEM(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    ON(AppCompatDelegate.MODE_NIGHT_YES),
    OFF(AppCompatDelegate.MODE_NIGHT_NO)

}