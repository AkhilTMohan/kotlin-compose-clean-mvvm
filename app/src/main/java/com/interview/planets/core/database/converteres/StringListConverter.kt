package com.interview.planets.core.database.converteres

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.interview.planets.core.helpers.SingletonUtils
import javax.inject.Inject

class StringListConverter {
    @TypeConverter
    fun fromJsonToList(json: String?): List<String?>? {
        if (json == null) return null
        val listType = object : TypeToken<List<String>>() {}.type
        return SingletonUtils.getGson().fromJson(json, listType)
    }

    @TypeConverter
    fun fromListToJson(list: List<String?>?): String? {
        if (list == null) return null
        return SingletonUtils.getGson().toJson(list)
    }
}