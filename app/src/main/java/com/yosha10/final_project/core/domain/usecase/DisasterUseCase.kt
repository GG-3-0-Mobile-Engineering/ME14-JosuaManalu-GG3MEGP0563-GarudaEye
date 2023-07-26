package com.yosha10.final_project.core.domain.usecase

import androidx.lifecycle.LiveData
import com.yosha10.final_project.core.data.Resource
import com.yosha10.final_project.core.domain.model.UrunDayaReport

interface DisasterUseCase {
    fun getUrunDayaReport(
        admin: String? = null,
        disaster: String? = null
    ): LiveData<Resource<List<UrunDayaReport>>>
}