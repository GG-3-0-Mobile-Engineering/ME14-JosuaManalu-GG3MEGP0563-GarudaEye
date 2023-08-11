package com.yosha10.final_project.core.domain.usecase

import com.yosha10.final_project.core.data.Resource
import com.yosha10.final_project.core.domain.model.Disaster
import com.yosha10.final_project.core.domain.repository.IDisasterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DisasterInteractor @Inject constructor(private val disasterRepository: IDisasterRepository) : DisasterUseCase {

    override fun getAllReport(
        admin: String?,
        disasterType: String?
    ): Flow<Resource<List<Disaster>>> {
        return disasterRepository.getAllReport(admin, disasterType)
    }
}