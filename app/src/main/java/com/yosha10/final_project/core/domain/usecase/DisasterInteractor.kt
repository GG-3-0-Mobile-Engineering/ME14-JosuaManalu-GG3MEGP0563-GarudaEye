package com.yosha10.final_project.core.domain.usecase

import androidx.lifecycle.LiveData
import com.yosha10.final_project.core.data.Resource
import com.yosha10.final_project.core.domain.model.UrunDayaReport
import com.yosha10.final_project.core.domain.repository.IDisasterRepository

class DisasterInteractor(private val disasterRepository: IDisasterRepository) : DisasterUseCase {

    override fun getUrunDayaReport(
        admin: String?,
        disaster: String?
    ): LiveData<Resource<List<UrunDayaReport>>> {
        return disasterRepository.getUrunDayaReport(admin, disaster)
    }

}