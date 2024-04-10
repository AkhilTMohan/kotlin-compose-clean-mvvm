package com.interview.planets.data.network

import com.interview.planets.data.models.PlanetResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanetAPIInterface {
    @GET("api/planets/")
    suspend fun getPlanets(
        @Query("page") page: Int
    ): PlanetResponse
}