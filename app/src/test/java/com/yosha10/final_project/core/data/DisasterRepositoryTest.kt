package com.yosha10.final_project.core.data

import com.yosha10.final_project.core.data.source.local.LocalDataSource
import com.yosha10.final_project.core.data.source.remote.RemoteDataSource
import com.yosha10.final_project.core.data.source.remote.network.ApiResponse
import com.yosha10.final_project.core.domain.model.Disaster
import com.yosha10.final_project.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class DisasterRepositoryTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    private lateinit var disasterRepository: DisasterRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        disasterRepository = DisasterRepository(remoteDataSource, localDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `use getAllReport when admin and disasterType are null should return list of disasters`() =
        runTest {
            val disasters = listOf(
                Disaster(
                    "322065",
                    "2023-08-14T07:09:10.538Z",
                    "confirmed",
                    "https://images.petabencana.id/b86d0fb5-67b4-41ec-9869-a022ba0efb33.jpg",
                    "flood",
                    "null",
                    "tes",
                    "ID-JK",
                    106.8851337114,
                    -6.1846953571,
                )
            )

            val disasterEntities = DataMapper.mapDomainToEntities(disasters)
            val disasterResponses = DataMapper.mapEntitiesToResponse(disasterEntities)
            
            `when`(localDataSource.getAllDisaster(null, null)).thenReturn(flowOf(disasterEntities))
            `when`(remoteDataSource.getAllReport(null, null)).thenReturn(flowOf(ApiResponse.Success(disasterResponses)))

            val localData = localDataSource.getAllDisaster(null, null).first()
            assertEquals(disasterEntities, localData)

            val remoteData = remoteDataSource.getAllReport(null, null).first()
            assertEquals(ApiResponse.Success(disasterResponses), remoteData)

            `when`(localDataSource.getAllDisaster(null, null)).thenReturn(flowOf(disasterEntities))
            `when`(remoteDataSource.getAllReport(null, null)).thenReturn(flowOf(ApiResponse.Success(disasterResponses)))
            val result = disasterRepository.getAllReport(null, null).toList()
            assertTrue(result.contains(Resource.Success(disasters)))
        }
}