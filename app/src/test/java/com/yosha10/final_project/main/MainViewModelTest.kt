package com.yosha10.final_project.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.yosha10.final_project.core.data.Resource
import com.yosha10.final_project.core.domain.model.Disaster
import com.yosha10.final_project.core.domain.usecase.DisasterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var disasterUseCase: DisasterUseCase

    @Mock
    private lateinit var observer: Observer<Resource<List<Disaster>>>

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        mainViewModel = MainViewModel(disasterUseCase)
    }

    @After
    fun tearDown(){
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

            `when`(disasterUseCase.getAllReport(null, null)).thenReturn(
                flowOf(
                    Resource.Success(
                        disasters
                    )
                )
            )
            mainViewModel.getAllReport().observeForever(observer)
            observer.onChanged(Resource.Success(disasters))
        }
}