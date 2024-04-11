package com.interview.planets.data

import com.google.gson.Gson
import com.interview.planets.data.models.Planet
import com.interview.planets.data.network.PlanetAPIInterface
import com.interview.planets.helpers.FileReader
import io.mockk.clearAllMocks
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlanetRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var planetAPIInterface: PlanetAPIInterface

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        planetAPIInterface =
            Retrofit.Builder().baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PlanetAPIInterface::class.java)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
        mockWebServer.shutdown()
    }

    @Test
    fun getPlanetDetailsFromServer() = runBlocking {
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