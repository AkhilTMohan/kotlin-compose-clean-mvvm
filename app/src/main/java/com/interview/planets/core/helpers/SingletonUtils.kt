package com.interview.planets.core.helpers

import com.google.gson.Gson

object SingletonUtils {
    private val gson = Gson()
    fun getGson() = gson
}