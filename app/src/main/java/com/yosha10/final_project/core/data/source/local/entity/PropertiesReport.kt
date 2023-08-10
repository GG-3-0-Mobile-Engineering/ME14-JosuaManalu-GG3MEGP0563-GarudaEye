package com.yosha10.final_project.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "properties_report")
data class PropertiesReport(
    @PrimaryKey
    @ColumnInfo(name = "pkeyId")
    val pkeyId: String? = null,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "disaster_type")
    val disasterType: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "source")
    val source: String? = null,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "url")
    val url: String? = null,

    @ColumnInfo(name = "partner_icon")
    val partnerIcon: Any? = null,

    @ColumnInfo(name = "text")
    val text: String? = null,

    @ColumnInfo(name = "partner_code")
    val partnerCode: Any? = null,

    @ColumnInfo(name = "status")
    val status: String? = null
)
