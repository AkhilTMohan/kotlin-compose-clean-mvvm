package com.interview.planets.core.helpers

import androidx.compose.ui.platform.LocalConfiguration
import com.google.gson.Gson

object SingletonUtils {
    private val gson = Gson()
    fun getGson() = gson
}