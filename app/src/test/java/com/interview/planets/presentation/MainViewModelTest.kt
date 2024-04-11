package com.interview.planets.presentation

import com.google.gson.Gson
import com.interview.planets.core.database.PlanetDatabase
import com.interview.planets.core.database.PlanetsDao
import com.interview.planets.data.PlanetRepository
import com.interview.planets.data.models.Planet
import com.interview.planets.data.network.PlanetAPIInterface
import com.interview.planets.data.network.Response
import com.interview.planets.domain.PlanetUseCase
import com.interview.planets.helpers.FileReader
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var planetUseCase: PlanetUseCase

    private val planetsDao: PlanetsDao = mockk(relaxed = true)
    private val planetDatabase: PlanetDatabase = mockk(relaxed = true)
    private lateinit var planetAPIInterface: PlanetAPIInterface
    private val planetRepository: PlanetRepository = mockk(relaxed = true)

    private lateinit var mockWebServer: MockWebServer

    // TODO: NOT COVERED ALL TEST CASES

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        mockWebServer = MockWebServer()
        planetAPIInterface =
            Retrofit.Builder().baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PlanetAPIInterface::class.java)

        planetUseCase =
            PlanetUseCase(planetsDao, planetDatabase, planetAPIInterface, planetRepository)
        mainViewModel = MainViewModel(useCase = planetUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }

    @Test
    fun `getPlanets updates UI state with expected PagingData`() = runBlocking {
        mainViewModel.getPlanets()
        val uiState = mainViewModel.uiState.value
        assertEquals(true, uiState.planets != null)
    }

    @Test
    fun `updateBaseUIState with error is ERROR`() {
        mainViewModel.updateBaseUIState(
            MainViewModel.BaseUIState(
                isError = Response.ErrorTypes.ERROR
            )
        )
        assertEquals(Response.ErrorTypes.ERROR, mainViewModel.uiState.value.isError)
    }


    @Test
    fun `updateBaseUIState with error is NETWORK_ERROR`() {
        mainViewModel.updateBaseUIState(
            MainViewModel.BaseUIState(
                isError = Response.ErrorTypes.NO_NETWORK
            )
        )
        assertEquals(Response.ErrorTypes.NO_NETWORK, mainViewModel.uiState.value.isError)
    }


    @Test
    fun fetchPlanetDataFromServer() = runBlocking {
        val mockResponse = MockResponse()
        val json = FileReader.readStringFromFile("/response.json")
        val planet = Gson().fromJson(json, Planet::class.java)
        mockResponse.setBody(json)
        mockWebServer.enqueue(mockResponse)
        val response = planetAPIInterface.getPlanetDetails("")
        mockWebServer.takeRequest()
        assertEquals(planet, response)
    }
}