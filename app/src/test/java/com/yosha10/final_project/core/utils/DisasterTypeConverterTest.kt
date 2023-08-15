package com.yosha10.final_project.core.utils

import org.junit.Assert.*
import org.junit.Test

class DisasterTypeConverterTest {
    @Test
    fun `use getDisasterTypeText when given DisasterType should return IDvalue`() {
        assertEquals(DisasterType.FLOOD.IDvalue, DisasterTypeConverter.getDisasterTypeText(DisasterType.FLOOD))
        assertEquals(DisasterType.EARTHQUAKE.IDvalue, DisasterTypeConverter.getDisasterTypeText(DisasterType.EARTHQUAKE))
        assertEquals(DisasterType.FIRE.IDvalue, DisasterTypeConverter.getDisasterTypeText(DisasterType.FIRE))
        assertEquals(DisasterType.HAZE.IDvalue, DisasterTypeConverter.getDisasterTypeText(DisasterType.HAZE))
        assertEquals(DisasterType.WIND.IDvalue, DisasterTypeConverter.getDisasterTypeText(DisasterType.WIND))
        assertEquals(DisasterType.VOLCANO.IDvalue, DisasterTypeConverter.getDisasterTypeText(DisasterType.VOLCANO))
    }

    @Test
    fun `use getDisasterTypeColor when given DisasterType should return colorResId`() {
        assertEquals(DisasterType.FLOOD.colorResId, DisasterTypeConverter.getDisasterTypeColor(DisasterType.FLOOD))
        assertEquals(DisasterType.EARTHQUAKE.colorResId, DisasterTypeConverter.getDisasterTypeColor(DisasterType.EARTHQUAKE))
        assertEquals(DisasterType.FIRE.colorResId, DisasterTypeConverter.getDisasterTypeColor(DisasterType.FIRE))
        assertEquals(DisasterType.HAZE.colorResId, DisasterTypeConverter.getDisasterTypeColor(DisasterType.HAZE))
        assertEquals(DisasterType.WIND.colorResId, DisasterTypeConverter.getDisasterTypeColor(DisasterType.WIND))
        assertEquals(DisasterType.VOLCANO.colorResId, DisasterTypeConverter.getDisasterTypeColor(DisasterType.VOLCANO))
    }
}