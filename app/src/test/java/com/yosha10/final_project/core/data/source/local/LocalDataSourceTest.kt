package com.yosha10.final_project.core.data.source.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.yosha10.final_project.core.data.source.local.entity.DisasterEntity
import com.yosha10.final_project.core.data.source.local.room.DisasterDao
import com.yosha10.final_project.core.data.source.local.room.DisasterDatabase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class LocalDataSourceTest {
    @MockK
    private lateinit var disasterDao: DisasterDao

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localDataSource = LocalDataSource(disasterDao)
    }

    @Test
    fun getAllDisaster() = runBlocking {
        val disasterList = listOf(
            DisasterEntity("1", "admin1", "type1", "location1", "description1"),
            DisasterEntity("2", "admin2", "type2", "location2", "description2")
        )
        coEvery { disasterDao.getAllDisaster(any(), any()) } returns flowOf(disasterList)

        val disasters = localDataSource.getAllDisaster().first()
        assertEquals(disasters, disasterList)
    }
}