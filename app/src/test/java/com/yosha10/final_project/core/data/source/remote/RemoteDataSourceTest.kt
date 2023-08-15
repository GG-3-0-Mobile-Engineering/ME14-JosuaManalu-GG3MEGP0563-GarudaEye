package com.yosha10.final_project.core.data.source.remote

import com.yosha10.final_project.core.data.source.remote.RemoteDataSource
import com.yosha10.final_project.core.data.source.remote.network.ApiResponse
import com.yosha10.final_project.core.data.source.remote.network.ApiService
import com.yosha10.final_project.core.data.source.remote.response.DisasterGeometriesResponse
import com.yosha10.final_project.core.data.source.remote.response.DisasterObjectsResponse
import com.yosha10.final_project.core.data.source.remote.response.DisasterOutputResponse
import com.yosha10.final_project.core.data.source.remote.response.DisasterReportResponse
import com.yosha10.final_project.core.data.source.remote.response.DisasterResultResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class RemoteDataSourceTest {
    @Test
    fun `use getAllReport when admin and disasterType are null should return list of DisasterGeometriesResponse`() = runBlocking {
        val disasterGeometriesResponse = DisasterGeometriesResponse(
            coordinates = listOf(1.0, 2.0),
            disasterResponse = null
        )
        val disasterReportResponse = DisasterReportResponse(
            disasterResultResponse = DisasterResultResponse(
                disasterObjectsResponse = DisasterObjectsResponse(
                    disasterOutputResponse = DisasterOutputResponse(
                        disasterGeometriesResponse = listOf(disasterGeometriesResponse)
                    )
                )
            )
        )
        val apiService = mock(ApiService::class.java)
        `when`(apiService.getAllReport(null, null)).thenReturn(disasterReportResponse)
        val remoteDataSource = RemoteDataSource(apiService)
        val result = remoteDataSource.getAllReport().first()
        assertEquals(ApiResponse.Success(listOf(disasterGeometriesResponse)), result)
    }
}
