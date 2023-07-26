package com.yosha10.final_project.core.domain.model

data class UrunDayaReport(
    val type: String,
    val properties: PropertiesReport,
    val coordinates: List<Double>
)