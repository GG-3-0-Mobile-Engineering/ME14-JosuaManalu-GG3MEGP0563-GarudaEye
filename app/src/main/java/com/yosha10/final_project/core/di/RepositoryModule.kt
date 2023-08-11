package com.yosha10.final_project.core.di

import com.yosha10.final_project.core.data.DisasterRepository
import com.yosha10.final_project.core.domain.repository.IDisasterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(disasterRepository: DisasterRepository): IDisasterRepository
}