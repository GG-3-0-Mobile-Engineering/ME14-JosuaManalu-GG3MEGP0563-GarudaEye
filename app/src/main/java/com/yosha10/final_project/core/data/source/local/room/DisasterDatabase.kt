package com.yosha10.final_project.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yosha10.final_project.core.data.source.local.entity.PropertiesReportEntity

@Database(entities = [PropertiesReportEntity::class], version = 1, exportSchema = false)
abstract class DisasterDatabase: RoomDatabase() {

    abstract fun disasterDao(): DisasterDao
}