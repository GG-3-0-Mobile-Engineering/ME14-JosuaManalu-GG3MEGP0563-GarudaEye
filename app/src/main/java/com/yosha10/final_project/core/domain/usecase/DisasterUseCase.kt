package com.yosha10.final_project.core.domain.usecase

import com.yosha10.final_project.core.data.Resource
import com.yosha10.final_project.core.domain.model.Disaster
import kotlinx.coroutines.flow.Flow

interface DisasterUseCase {
    fun getAllReport(
        admin: String? = null,
        disasterType: String? = null,
    ): Flow<Resource<List<Disaster>>>
}