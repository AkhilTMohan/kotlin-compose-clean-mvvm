package com.interview.planets.domain

import com.interview.planets.core.database.PlanetDatabase
import com.interview.planets.core.database.PlanetsDao
import com.interview.planets.data.PlanetRepository
import com.interview.planets.data.models.Planet
import com.interview.planets.data.network.PlanetAPIInterface
import com.interview.planets.data.network.Response
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlanetUseCaseTest {
    private val planetsDao: PlanetsDao = mockk()
    private val planetDatabase: PlanetDatabase = mockk()
    private val planetAPIInterface: PlanetAPIInterface = mockk()

    private lateinit var planetUseCase: PlanetUseCase
    private lateinit var planetRepository: PlanetRepository

    @BeforeEach
    fun setUp() {
        planetRepository = PlanetRepository(planetsDao, planetAPIInterface)
        planetUseCase = PlanetUseCase(
            planetsDao,
            planetDatabase,
            planetAPIInterface,
            planetRepository
        )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun fetchPlanetDataFromServer() = runBlocking {
        val planet = Planet(1, 1)
        val response = Response.Success<Planet>(planet)
        coEvery { planetAPIInterface.getPlanetDetails(any()) } returns planet
        coEvery { planetsDao.updatePlanets(any()) } returns Unit
        val repoResponse = planetRepository.getPlanetDetailsFromServer(planet)
        val useCaseResponse = planetUseCase.fetchPlanetDataFromServer(Planet(1, 1))
        assertEquals(response, repoResponse)
        assertEquals(response, useCaseResponse)
    }

}