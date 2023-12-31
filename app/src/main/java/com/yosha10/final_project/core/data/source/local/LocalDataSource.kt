package com.yosha10.final_project.core.data.source.local

import com.yosha10.final_project.core.data.source.local.entity.DisasterEntity
import com.yosha10.final_project.core.data.source.local.room.DisasterDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val disasterDao: DisasterDao) {
    fun getAllDisaster(
        admin: String? = null,
        disasterType: String? = null
    ): Flow<List<DisasterEntity>> = disasterDao.getAllDisaster(admin, disasterType)

    suspend fun insertDisaster(disasterList: List<DisasterEntity>) = disasterDao.insertDisaster(disasterList)

    suspend fun deleteAllDisaster() = disasterDao.deleteAllDisaster()
}