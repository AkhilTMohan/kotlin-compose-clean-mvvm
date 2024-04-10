package com.interview.planets.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.interview.planets.core.database.converteres.ResultConverter
import com.interview.planets.core.database.converteres.StringListConverter
import com.interview.planets.data.models.PageDetails
import com.interview.planets.data.models.Planet


@Database(entities = [Planet::class, PageDetails::class], version = 1, exportSchema = false)
@TypeConverters(ResultConverter::class, StringListConverter::class)
abstract class PlanetDatabase : RoomDatabase() {
    abstract fun planetsDao(): PlanetsDao
}