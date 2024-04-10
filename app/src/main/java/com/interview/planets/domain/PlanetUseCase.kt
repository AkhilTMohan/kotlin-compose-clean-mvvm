package com.interview.planets.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.interview.planets.core.database.PlanetDatabase
import com.interview.planets.core.database.PlanetsDao
import com.interview.planets.core.helpers.PlanetConstants.PAGE_SIZE
import com.interview.planets.data.models.Planet
import com.interview.planets.data.network.PlanetAPIInterface
import com.interview.planets.data.paging.PlanetRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlanetUseCase @Inject constructor(
    private val planetsDao: PlanetsDao,
    private val planetDatabase: PlanetDatabase,
    private val planetAPIInterface: PlanetAPIInterface
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getPlanets(): Flow<PagingData<Planet>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = 1,
            ),
            initialKey = 1,
            pagingSourceFactory = {
                planetsDao.getAllPlanets()
            },
            remoteMediator = PlanetRemoteMediator(
                planetDatabase = planetDatabase,
                planetAPIInterface = planetAPIInterface
            )
        ).flow
}