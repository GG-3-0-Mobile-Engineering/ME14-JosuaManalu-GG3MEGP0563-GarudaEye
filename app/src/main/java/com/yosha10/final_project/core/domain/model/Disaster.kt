package com.yosha10.final_project.core.domain.model

data class PropertiesReport(
    val pkey: String,
    val created_at: String,
    val status: String,
    val image_url: String,
    val disaster_type: String,
    val title: String,
    val text: String,
    val admin: String,
    val lat: Double,
    val lon: Double,
)

