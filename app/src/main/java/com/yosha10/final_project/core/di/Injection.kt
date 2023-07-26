package com.yosha10.final_project.core.di

import android.content.Context
import com.yosha10.final_project.core.data.DisasterRepository
import com.yosha10.final_project.core.data.source.remote.RemoteDataSource
import com.yosha10.final_project.core.data.source.remote.network.ApiConfig
import com.yosha10.final_project.core.domain.repository.IDisasterRepository
import com.yosha10.final_project.core.domain.usecase.DisasterInteractor
import com.yosha10.final_project.core.domain.usecase.DisasterUseCase

object Injection {

    fun provideRepository(context: Context): IDisasterRepository {
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())

        return DisasterRepository.getInstance(remoteDataSource)
    }

    fun provideDisasterUseCase(context: Context): DisasterUseCase {
        val repository = provideRepository(context)
        return DisasterInteractor(repository)
    }
}