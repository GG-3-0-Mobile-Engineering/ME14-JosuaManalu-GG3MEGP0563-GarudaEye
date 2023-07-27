package com.yosha10.final_project.core.utils

import com.yosha10.final_project.R

enum class DisasterType(val IDvalue: String, val value: String, val colorResId: Int) {
    FLOOD("Banjir", "flood", R.color.colorFlood),
    EARTHQUAKE("Gempa Bumi", "earthquake", R.color.colorEarthquake),
    FIRE("Kebakaran Hutan", "fire", R.color.colorFire),
    HAZE("Kabut Asap", "haze", R.color.colorHaze),
    WIND("Angin Kencang", "wind", R.color.colorWind),
    VOLCANO("Gunung Api", "volcano", R.color.colorVolcano)
}


