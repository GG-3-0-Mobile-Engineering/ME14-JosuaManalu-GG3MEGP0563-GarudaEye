package com.yosha10.final_project.core.data.source.remote

import androidx.lifecycle.LiveData
import com.yosha10.final_project.core.data.source.remote.network.ApiResponse
import com.yosha10.final_project.core.data.source.remote.network.ApiService
import com.yosha10.final_project.core.data.source.remote.response.GeometriesItem

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RemoteDataSource(service)
            }
    }

}