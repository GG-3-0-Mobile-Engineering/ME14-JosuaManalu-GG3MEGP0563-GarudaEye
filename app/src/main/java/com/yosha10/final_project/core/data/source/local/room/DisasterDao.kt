package com.yosha10.final_project.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yosha10.final_project.core.data.source.local.entity.DisasterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DisasterDao {
    @Query("SELECT * FROM disaster " +
            "WHERE (:admin IS NULL OR admin = :admin) " +
            "AND (:disasterType IS NULL OR disaster_type = :disasterType)")
    fun getAllDisaster(
        admin: String? = null,
        disasterType: String? = null
    ): Flow<List<DisasterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDisaster(disaster: List<DisasterEntity>)

    @Query("DELETE FROM disaster")
    suspend fun deleteAllDisaster()
}