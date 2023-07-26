package com.yosha10.final_project.core.data.source.remote.network

import com.yosha10.final_project.core.data.source.remote.response.UrunDayaReportResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("reports")
    fun getAllReport(
        @Query("admin") admin: String? = null,
        @Query("disaster") disaster: String? = null,
        @Query("timeperiod") timeperiod: Int? = null,
    ): Call<UrunDayaReportResponse>
}