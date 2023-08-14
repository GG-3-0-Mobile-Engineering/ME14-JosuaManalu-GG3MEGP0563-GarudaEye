package com.yosha10.final_project.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disaster(
    val pkey: String,
    val createdAt: String,
    val status: String,
    val imageUrl: String,
    val disasterType: String,
    val title: String,
    val text: String,
    val admin: String,
    val lat: Double,
    val lon: Double,
): Parcelable