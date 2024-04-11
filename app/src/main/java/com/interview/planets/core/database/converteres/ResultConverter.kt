package com.interview.planets.core.database.converteres

import androidx.room.TypeConverter
import com.interview.planets.core.helpers.SingletonUtils
import com.interview.planets.data.models.Planet

class ResultConverter {

    @TypeConverter
    fun fromJsonToResult(json: String?): Planet? {
        if (json == null) return null
        return SingletonUtils.getGson().fromJson(json, Planet::class.java)
    }

    @TypeConverter
    fun fromResultToJson(planet: Planet?): String? {
        if (planet == null) return null
        return SingletonUtils.getGson().toJson(planet)
    }
}