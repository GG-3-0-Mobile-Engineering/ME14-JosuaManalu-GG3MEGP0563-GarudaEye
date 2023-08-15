package com.yosha10.final_project.core.utils

import com.yosha10.final_project.core.data.source.local.entity.DisasterEntity
import com.yosha10.final_project.core.data.source.remote.response.DisasterGeometriesResponse
import com.yosha10.final_project.core.data.source.remote.response.DisasterResponse
import com.yosha10.final_project.core.data.source.remote.response.DisasterTagsResponse
import com.yosha10.final_project.core.domain.model.Disaster
import org.junit.Assert.*
import org.junit.Test

class DataMapperTest {
    @Test
    fun `use mapResponseToEntities when given DisasterGeometriesResponse should return list of DisasterEntity`() {
        val disasterGeometriesResponse = DisasterGeometriesResponse(
            coordinates = listOf(1.0, 2.0),
            disasterResponse = DisasterResponse(
                pkey = "pkey",
                imageUrl = "imageUrl",
                disasterType = "disasterType",
                createdAt = "createdAt",
                title = "title",
                text = "text",
                status = "status",
                disasterTagsResponse = DisasterTagsResponse(
                    instanceRegionCode = "instanceRegionCode"
                )
            )
        )
        val expectedDisasterEntity = DisasterEntity(
            pkeyId = "pkey",
            imageUrl = "imageUrl",
            disasterType = "disasterType",
            createdAt = "createdAt",
            title = "title",
            text = "text",
            status = "status",
            admin = "instanceRegionCode",
            lat = 2.0,
            lon = 1.0,
        )
        val result = DataMapper.mapResponseToEntities(listOf(disasterGeometriesResponse))
        assertEquals(listOf(expectedDisasterEntity), result)
    }

    @Test
    fun `use mapEntitiesToDomain when given DisasterEntity should return list of Disaster`() {
        val disasterEntity = DisasterEntity(
            pkeyId = "pkeyId",
            imageUrl = "imageUrl",
            disasterType = "disasterType",
            createdAt = "createdAt",
            title = "title",
            text = "text",
            status = "status",
            admin = "admin",
            lat = 1.0,
            lon = 2.0,
        )
        val expectedDisaster = Disaster(
            pkey = "pkeyId",
            imageUrl = "imageUrl",
            disasterType = "disasterType",
            createdAt = "createdAt",
            title = "title",
            text = "text",
            status = "status",
            admin = "admin",
            lat = 1.0,
            lon = 2.0,
        )
        val result= DataMapper.mapEntitiesToDomain(listOf(disasterEntity))
        assertEquals(listOf(expectedDisaster), result)
    }

    @Test
    fun `use mapDomainToEntities when given Disaster should return list of DisasterEntity`() {
        val disaster= Disaster(
            pkey=  "pkeyId" ,
            imageUrl=  "imageUrl" ,
            disasterType=  "disasterType" ,
            createdAt=  "createdAt" ,
            title=  "title" ,
            text=  "text" ,
            status=  "status" ,
            admin=  "admin" ,
            lat= 1.0,
            lon= 2.0,
        )
        val expectedDisasterEntity= DisasterEntity(
            pkeyId=  "pkeyId" ,
            imageUrl=  "imageUrl" ,
            disasterType=  "disasterType" ,
            createdAt=  "createdAt" ,
            title=  "title" ,
            text=  "text" ,
            status=  "status" ,
            admin=  "admin" ,
            lat= 1.0,
            lon= 2.0,
        )
        val result= DataMapper.mapDomainToEntities(listOf(disaster))
        assertEquals(listOf(expectedDisasterEntity), result)
    }
}