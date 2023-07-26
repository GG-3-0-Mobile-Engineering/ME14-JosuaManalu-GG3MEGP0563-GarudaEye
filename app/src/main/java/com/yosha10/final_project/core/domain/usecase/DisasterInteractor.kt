package com.yosha10.final_project.core.domain.usecase

import androidx.lifecycle.LiveData
import com.yosha10.final_project.core.data.Resource
import com.yosha10.final_project.core.domain.model.UrunDayaReport
import com.yosha10.final_project.core.domain.repository.IDisasterRepository

class DisasterInteractor(private val disasterRepository: IDisasterRepository) : DisasterUseCase {

    override fun getAllReport(
        admin: String?,
        disaster: String?,
        timeperiod: Int?
    ): LiveData<Resource<List<UrunDayaReport>>> {
        return disasterRepository.getAllReport(admin, disaster, timeperiod)
    }

}