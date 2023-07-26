package com.yosha10.final_project.core.utils

import com.yosha10.final_project.core.data.source.remote.response.GeometriesItem
import com.yosha10.final_project.core.data.source.remote.response.Properties
import com.yosha10.final_project.core.data.source.remote.response.UrunDayaReportResponse
import com.yosha10.final_project.core.domain.model.PropertiesReport
import com.yosha10.final_project.core.domain.model.UrunDayaReport
import java.text.SimpleDateFormat
import java.util.Locale

object DataMapper {
    fun mapResponseToDomain(input: List<GeometriesItem>):List<UrunDayaReport>{
        val reportList = ArrayList<UrunDayaReport>()
        input.map {
            val report = UrunDayaReport(
                type = it.type.orEmpty(),
                properties = PropertiesReport(
                    pkey = it.properties?.pkey.orEmpty(),
                    created_at = it.properties?.createdAt.orEmpty(),
                    source = it.properties?.source.orEmpty(),
                    status = it.properties?.status.orEmpty(),
                    url = it.properties?.url.orEmpty(),
                    image_url = it.properties?.imageUrl.orEmpty(),
                    disaster_type = it.properties?.disasterType.orEmpty(),
                    text = it.properties?.text.orEmpty(),
                    title = it.properties?.title.orEmpty(),
                ),
                coordinates = (it.coordinates ?: listOf(0.0, 0.0)) as List<Double>
            )
            reportList.add(report)
        }
        return reportList
    }

    private fun formatCreatedAtDate(date: String): Long {
        return date?.let {
            SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.US).parse(it).time
        } ?: Long.MIN_VALUE
    }
}