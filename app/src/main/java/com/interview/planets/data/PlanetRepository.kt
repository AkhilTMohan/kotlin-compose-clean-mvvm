package com.interview.planets.data

import com.interview.planets.core.database.PlanetsDao
import com.interview.planets.data.models.Planet
import com.interview.planets.data.network.PlanetAPIInterface
import com.interview.planets.data.network.Response
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class PlanetRepository @Inject constructor(
    private val planetsDao: PlanetsDao, private val planetAPIInterface: PlanetAPIInterface
) {
    suspend fun getPlanetDetailsFromServer(planetReq: Planet?): Response<Planet> {
        return try {
            withContext(IO) {
                val planet = planetAPIInterface.getPlanetDetails(url = planetReq?.url)
                planet?.let {
                    planetsDao.updatePlanets(
                        it.copy(
                            index = planetReq?.index ?: 0,
                            page = planetReq?.page ?: 0
                        )
                    )
                }
                Response.Success(planet)
            }
        } catch (error: IOException) {
            Response.Error(error)
        } catch (error: HttpException) {
            Response.Error(error)
        }
    }

}