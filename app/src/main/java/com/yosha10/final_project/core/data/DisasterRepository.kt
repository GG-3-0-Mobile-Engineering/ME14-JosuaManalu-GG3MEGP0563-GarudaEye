package com.yosha10.final_project.core.data

import com.yosha10.final_project.core.data.source.local.LocalDataSource
import com.yosha10.final_project.core.data.source.remote.RemoteDataSource
import com.yosha10.final_project.core.data.source.remote.network.ApiResponse
import com.yosha10.final_project.core.data.source.remote.response.DisasterGeometriesResponse
import com.yosha10.final_project.core.domain.model.Disaster
import com.yosha10.final_project.core.domain.repository.IDisasterRepository
import com.yosha10.final_project.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DisasterRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IDisasterRepository {
    override fun getAllReport(
        admin: String?,
        disasterType: String?
    ): Flow<Resource<List<Disaster>>> =
    object: NetworkBoundResource<List<Disaster>, List<DisasterGeometriesResponse>>() {
        override fun loadFromDB(): Flow<List<Disaster>> {
            return localDataSource.getAllDisaster(admin, disasterType).map { DataMapper.mapEntitiesToDomain(it) }
        }

        override suspend fun createCall(): Flow<ApiResponse<List<DisasterGeometriesResponse>>> {
            return remoteDataSource.getAllReport(admin, disasterType)
        }

        override suspend fun saveCallResult(data: List<DisasterGeometriesResponse>) {
            val disasterList = DataMapper.mapResponseToEntities(data)
//            localDataSource.deleteAllDisaster()
            localDataSource.insertDisaster(disasterList)
        }

        override fun shouldFetch(data: List<Disaster>?): Boolean {
            return data.isNullOrEmpty()
        }
    }.asFlow()
}