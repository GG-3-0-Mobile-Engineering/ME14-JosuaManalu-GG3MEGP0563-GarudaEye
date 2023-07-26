package com.yosha10.final_project.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yosha10.final_project.core.data.source.remote.network.ApiResponse
import com.yosha10.final_project.core.data.source.remote.network.ApiService
import com.yosha10.final_project.core.data.source.remote.response.GeometriesItem
import com.yosha10.final_project.core.data.source.remote.response.UrunDayaReportResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RemoteDataSource(service)
            }
    }

    fun getAllReport(
        admin: String? = null,
        disaster: String? = null,
        timeperiod: Int? = null
    ): LiveData<ApiResponse<List<GeometriesItem>>> {

        val resultData = MutableLiveData<ApiResponse<List<GeometriesItem>>>()

        val client = apiService.getAllReport(admin, disaster, timeperiod)
        client.enqueue(object : Callback<UrunDayaReportResponse> {
            override fun onResponse(
                call: Call<UrunDayaReportResponse>,
                response: Response<UrunDayaReportResponse>
            ) {
                val dataArray = response.body()?.result?.objects?.output?.geometries
                resultData.value =
                    if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<UrunDayaReportResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }

        })
        return resultData
    }
}