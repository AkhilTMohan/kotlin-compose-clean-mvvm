package com.interview.planets.data.network

import com.interview.planets.data.models.Planet
import com.interview.planets.data.models.PlanetResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PlanetAPIInterface {
    @GET("api/planets/")
    suspend fun getPlanets(
        @Query("page") page: Int
    ): PlanetResponse?

    @GET
    suspend fun getPlanetDetails(
        @Url url: String?
    ): Planet?
}