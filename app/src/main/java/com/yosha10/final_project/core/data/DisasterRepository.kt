package com.yosha10.final_project.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yosha10.final_project.core.data.source.remote.RemoteDataSource
import com.yosha10.final_project.core.data.source.remote.network.ApiResponse
import com.yosha10.final_project.core.domain.model.UrunDayaReport
import com.yosha10.final_project.core.domain.repository.IDisasterRepository
import com.yosha10.final_project.core.utils.DataMapper

class DisasterRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
) : IDisasterRepository {

    companion object {
        @Volatile
        private var INSTANCE: DisasterRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource
        ): DisasterRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: DisasterRepository(remoteDataSource)
        }
    }

    override fun getAllReport(
        admin: String?,
        disaster: String?,
        timeperiod: Int?
    ): LiveData<Resource<List<UrunDayaReport>>> {
        val resultData = MutableLiveData<Resource<List<UrunDayaReport>>>()

        resultData.value = Resource.Loading(null)

        val remoteResponse = remoteDataSource.getAllReport(admin, disaster, timeperiod)

        remoteResponse.observeForever { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    val report = DataMapper.mapResponseToDomain(apiResponse.data)
                    resultData.value = Resource.Success(report)
                }

                is ApiResponse.Empty -> {
                    resultData.value = Resource.Success(emptyList())
                }

                is ApiResponse.Error -> {
                    resultData.value = Resource.Error(apiResponse.errorMessage, null)
                }
            }
        }
        return resultData
    }
}