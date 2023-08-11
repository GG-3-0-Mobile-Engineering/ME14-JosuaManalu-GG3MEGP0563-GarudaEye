package com.yosha10.final_project.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yosha10.final_project.core.domain.usecase.DisasterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val disasterUseCase: DisasterUseCase): ViewModel() {

    fun getAllReport(
        admin: String? = null,
        disasterType: String? = null,
    ) = disasterUseCase.getAllReport(admin, disasterType).asLiveData()
}