package com.interview.planets.data.network

import com.interview.planets.data.models.PlanetResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanetService {
    @GET("api/planets/")
    fun getPlanets(
        @Query("page") page: Int
    ):PlanetResponse
}