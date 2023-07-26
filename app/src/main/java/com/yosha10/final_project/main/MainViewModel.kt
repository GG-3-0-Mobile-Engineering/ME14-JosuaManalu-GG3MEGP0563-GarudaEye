package com.yosha10.final_project.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yosha10.final_project.core.data.Resource
import com.yosha10.final_project.core.domain.model.UrunDayaReport
import com.yosha10.final_project.core.domain.usecase.DisasterUseCase

class MainViewModel(private val disasterUseCase: DisasterUseCase): ViewModel() {

    fun getAllReport(
        admin: String? = null,
        disaster: String? = null,
        timeperiod: Int? = null,
    ): LiveData<Resource<List<UrunDayaReport>>> = disasterUseCase.getAllReport(admin, disaster, timeperiod)
}