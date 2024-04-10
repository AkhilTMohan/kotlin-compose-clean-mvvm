package com.interview.planets.core.database.converteres

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.interview.planets.data.models.Result
import javax.inject.Inject

class ResultConverter {

    @Inject
    lateinit var gson: Gson

    @TypeConverter
    fun fromJsonToResult(json: String?): Result? {
        if (json == null) return null
        return gson.fromJson(json, Result::class.java)
    }

    @TypeConverter
    fun fromResultToJson(result: Result?): String? {
        if (result == null) return null
        return gson.toJson(result)
    }
}