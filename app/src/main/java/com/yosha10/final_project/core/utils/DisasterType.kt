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

object DisasterTypeConverter {
    fun getDisasterTypeText(value: DisasterType): String {
        return when (value) {
            DisasterType.FLOOD -> DisasterType.FLOOD.IDvalue
            DisasterType.EARTHQUAKE -> DisasterType.EARTHQUAKE.IDvalue
            DisasterType.FIRE -> DisasterType.FIRE.IDvalue
            DisasterType.HAZE -> DisasterType.HAZE.IDvalue
            DisasterType.WIND -> DisasterType.WIND.IDvalue
            DisasterType.VOLCANO -> DisasterType.VOLCANO.IDvalue
        }
    }

    fun getDisasterTypeColor(value: DisasterType): Int {
        return when (value) {
            DisasterType.FLOOD -> DisasterType.FLOOD.colorResId
            DisasterType.EARTHQUAKE -> DisasterType.EARTHQUAKE.colorResId
            DisasterType.FIRE -> DisasterType.FIRE.colorResId
            DisasterType.HAZE -> DisasterType.HAZE.colorResId
            DisasterType.WIND -> DisasterType.WIND.colorResId
            DisasterType.VOLCANO -> DisasterType.VOLCANO.colorResId
        }
    }
}