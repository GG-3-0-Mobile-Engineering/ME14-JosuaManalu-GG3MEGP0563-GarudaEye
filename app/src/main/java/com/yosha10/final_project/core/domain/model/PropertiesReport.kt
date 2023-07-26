package com.yosha10.final_project.core.domain.model

data class PropertiesReport(
    val pkey: String,
    val created_at: String,
    val source: String,
    val status: String,
    val url: String?,
    val image_url: String?,
    val disaster_type: String,
//    val report_data: String?,
//    val tags: TagsReport,
    val title: String,
    val text: String
)

