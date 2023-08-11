package com.yosha10.final_project.core.data.source.local.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yosha10.final_project.core.data.source.local.entity.PropertiesReportEntity
import kotlinx.coroutines.flow.Flow

interface DisasterDao {
    @Query("SELECT * FROM properties_report " +
            "WHERE (:admin IS NULL OR admin = :admin) " +
            "AND (:disasterType IS NULL OR disaster_type = :disasterType)")
    fun getAllDisaster(
        admin: String,
        disasterType: String
    ): Flow<List<PropertiesReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDisaster(disaster: List<PropertiesReportEntity>)
    
    @Query("DELETE FROM properties_report")
    suspend fun deleteAllDisaster()
}