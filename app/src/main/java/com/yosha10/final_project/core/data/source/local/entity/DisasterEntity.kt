package com.yosha10.final_project.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "disaster")
data class DisasterEntity(
    @PrimaryKey
    @ColumnInfo(name = "pkeyId")
    val pkeyId: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "disaster_type")
    val disasterType: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "text")
    val text: String? = null,

    @ColumnInfo(name = "status")
    val status: String? = null,

    @ColumnInfo(name = "admin")
    val admin: String? = null,

    @ColumnInfo(name = "lat")
    val lat: Double? = 0.0,

    @ColumnInfo(name = "lon")
    val lon: Double? = 0.0,
)
