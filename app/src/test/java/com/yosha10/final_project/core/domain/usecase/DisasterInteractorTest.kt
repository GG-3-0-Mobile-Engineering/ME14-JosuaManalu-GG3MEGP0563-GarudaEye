package com.yosha10.final_project.core.domain.usecase

import com.yosha10.final_project.core.data.Resource
import com.yosha10.final_project.core.domain.model.Disaster
import com.yosha10.final_project.core.domain.repository.IDisasterRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DisasterInteractorTest {
    private lateinit var disasterRepository: IDisasterRepository
    private lateinit var disasterInteractor: DisasterInteractor

    @Before
    fun setUp() {
        disasterRepository = mockk()
        disasterInteractor = DisasterInteractor(disasterRepository)
    }

    @Test
    fun getAllReport() = runBlocking {
        val disasterList = listOf(
            Disaster("1", "createdAt1", "status1", "imageUrl1", "type1", "title1", "text1", "admin1", 0.0, 0.0),
            Disaster("2", "createdAt2", "status2", "imageUrl2", "type2", "title2", "text2", "admin2", 0.0, 0.0)
        )
        coEvery { disasterRepository.getAllReport(any(), any()) } returns flowOf(Resource.Success(disasterList))

        val result = disasterInteractor.getAllReport().first()
        assertEquals(result, Resource.Success(disasterList))
    }
}