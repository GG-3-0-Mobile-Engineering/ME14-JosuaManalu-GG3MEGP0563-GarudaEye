package com.yosha10.final_project.core.utils

import com.yosha10.final_project.core.data.source.local.entity.DisasterEntity
import com.yosha10.final_project.core.data.source.remote.response.DisasterGeometriesResponse
import com.yosha10.final_project.core.data.source.remote.response.DisasterResponse
import com.yosha10.final_project.core.data.source.remote.response.DisasterTagsResponse
import com.yosha10.final_project.core.domain.model.Disaster

object DataMapper {

    fun mapResponseToEntities(input: List<DisasterGeometriesResponse>): List<DisasterEntity> {
        val disasterList = ArrayList<DisasterEntity>()
        input.map {
            val disaster = DisasterEntity(
                pkeyId = it.disasterResponse?.pkey.orEmpty(),
                imageUrl = it.disasterResponse?.imageUrl.orEmpty(),
                disasterType = it.disasterResponse?.disasterType.orEmpty(),
                createdAt = it.disasterResponse?.createdAt.orEmpty(),
                title = it.disasterResponse?.title.orEmpty(),
                text = it.disasterResponse?.text.orEmpty(),
                status = it.disasterResponse?.status.orEmpty(),
                admin = it.disasterResponse?.disasterTagsResponse?.instanceRegionCode.orEmpty(),
                lat = it.coordinates?.get(1) ?: 0.0,
                lon = it.coordinates?.get(0) ?: 0.0,
            )
            disasterList.add(disaster)
        }
        return disasterList
    }

    fun mapEntitiesToDomain(input: List<DisasterEntity>): List<Disaster> =
        input.map {
            Disaster(
                pkey = it.pkeyId.orEmpty(),
                createdAt = it.createdAt.orEmpty(),
                status = it.status.orEmpty(),
                imageUrl = it.imageUrl.orEmpty(),
                disasterType = it.disasterType.orEmpty(),
                title = it.title.orEmpty(),
                text = it.text.orEmpty(),
                admin = it.admin.orEmpty(),
                lat = it.lat ?: 0.0,
                lon = it.lon ?: 0.0,
            )
        }

    fun mapDomainToEntities(input: List<Disaster>): List<DisasterEntity> =
        input.map {
            DisasterEntity(
                pkeyId = it.pkey,
                createdAt = it.createdAt,
                status = it.status,
                imageUrl = it.imageUrl,
                disasterType = it.disasterType,
                title = it.title,
                text = it.text,
                admin = it.admin,
                lat = it.lat,
                lon = it.lon,
            )
        }

    fun mapEntitiesToResponse(input: List<DisasterEntity>): List<DisasterGeometriesResponse> {
        val disasterList = ArrayList<DisasterGeometriesResponse>()
        input.map {
            val disaster = DisasterGeometriesResponse(
                coordinates = listOf(it.lon ?: 0.0, it.lat ?: 0.0),
                disasterResponse = DisasterResponse(
                    pkey = it.pkeyId.orEmpty(),
                    imageUrl = it.imageUrl.orEmpty(),
                    disasterType = it.disasterType.orEmpty(),
                    createdAt = it.createdAt.orEmpty(),
                    title = it.title.orEmpty(),
                    text = it.text.orEmpty(),
                    status = it.status.orEmpty(),
                    disasterTagsResponse = DisasterTagsResponse(
                        instanceRegionCode = it.admin.orEmpty()
                    )
                )
            )
            disasterList.add(disaster)
        }
        return disasterList
    }
}