package com.yosha10.final_project.core.utils

import com.yosha10.final_project.R

enum class DisasterType(val value: String, val colorResId: Int) {
    FLOOD("flood", R.color.colorFlood),
    EARTHQUAKE("earthquake", R.color.colorEarthquake),
    FIRE("fire", R.color.colorFire),
    HAZE("haze", R.color.colorHaze),
    WIND("wind", R.color.colorWind),
    VOLCANO("volcano", R.color.colorVolcano)
}


