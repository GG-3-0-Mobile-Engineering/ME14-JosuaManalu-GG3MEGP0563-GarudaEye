package com.yosha10.final_project.core.data.source.remote

import com.yosha10.final_project.core.data.source.remote.network.ApiResponse
import com.yosha10.final_project.core.data.source.remote.network.ApiService
import com.yosha10.final_project.core.data.source.remote.response.DisasterGeometriesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllReport(
        admin: String? = null,
        disasterType: String? = null,
    ): Flow<ApiResponse<List<DisasterGeometriesResponse>>> {
        return flow {
            try {
                val response = apiService.getAllReport(admin, disasterType)
                val dataArray = response.disasterResultResponse?.disasterObjectsResponse?.disasterOutputResponse?.disasterGeometriesResponse
                if (!dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}