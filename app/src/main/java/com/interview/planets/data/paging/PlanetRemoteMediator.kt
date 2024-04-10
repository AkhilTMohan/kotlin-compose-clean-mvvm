package com.interview.planets.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.interview.planets.core.database.PlanetDatabase
import com.interview.planets.data.models.Planet
import com.interview.planets.data.models.PageDetails
import com.interview.planets.data.network.PlanetAPIInterface
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class PlanetRemoteMediator(
    private val planetAPIInterface: PlanetAPIInterface,
    private val planetDatabase: PlanetDatabase,
) : RemoteMediator<Int, Planet>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Planet>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val pageDetails = getRemoteKeyClosestToCurrentPosition(state)
                pageDetails?.nextPage?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val previousPage = remoteKeys?.previousPage
                previousPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextPage = remoteKeys?.nextPage
                nextPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse = planetAPIInterface.getPlanets(page = page)

            val planets: List<Planet> = apiResponse.planets?.map {
                it?.page = page
                return@map it
            } as List<Planet>
            val endOfPaginationReached = planets.isEmpty()

            planetDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    planetDatabase.planetsDao().deleteAllPageDetails()
                    planetDatabase.planetsDao().deleteAllPlanets()
                }
                val previousPage = if (page > 1) page - 1 else null
                val nextPage = if (endOfPaginationReached) null else page + 1
                val pageDetails =
                    PageDetails(page = page, previousPage = previousPage, nextPage = nextPage)
                planetDatabase.planetsDao().insertPageDetails(pageDetails)
                planetDatabase.planetsDao().insertPlanets(planets)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Planet>): PageDetails? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.page?.let { page ->
                withContext(IO) { planetDatabase.planetsDao().getPageDetailsByPage(page) }
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Planet>): PageDetails? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.page?.let { page ->
            withContext(IO) { planetDatabase.planetsDao().getPageDetailsByPage(page) }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Planet>): PageDetails? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.page?.let { page ->
            withContext(IO) { planetDatabase.planetsDao().getPageDetailsByPage(page) }
        }
    }
}