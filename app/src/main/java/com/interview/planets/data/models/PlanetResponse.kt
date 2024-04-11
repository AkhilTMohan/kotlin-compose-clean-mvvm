package com.interview.planets.data.models


import com.google.gson.annotations.SerializedName

data class PlanetResponse(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("next")
    var next: String?,
    @SerializedName("previous")
    var previous: String?,
    @SerializedName("results")
    var planets: List<Planet?>?
)