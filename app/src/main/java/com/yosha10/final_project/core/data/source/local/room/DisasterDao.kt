package com.yosha10.final_project.core.data.source.local.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yosha10.final_project.core.data.source.local.entity.PropertiesReportEntity

interface DisasterDao {
    @Query("SELECT * FROM properties_report WHERE admin = :admin AND disaster_type = :disasterType")
    fun getAllDisaster(
        admin: String,
        disasterType: String
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDisaster(disaster: List<PropertiesReportEntity>)
}