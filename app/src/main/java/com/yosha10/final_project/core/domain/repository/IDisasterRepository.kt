package com.yosha10.final_project.core.domain.repository

import androidx.lifecycle.LiveData
import com.yosha10.final_project.core.data.Resource
import com.yosha10.final_project.core.domain.model.UrunDayaReport

interface IDisasterRepository {
    fun getUrunDayaReport(
        admin: String? = null,
        disaster: String? = null
    ): LiveData<Resource<List<UrunDayaReport>>>
}