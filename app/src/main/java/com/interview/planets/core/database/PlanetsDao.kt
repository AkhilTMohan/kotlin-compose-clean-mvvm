package com.interview.planets.core.database

import android.media.Image.Plane
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.interview.planets.data.models.PageDetails
import com.interview.planets.data.models.Planet

@Dao
interface PlanetsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlanets(planetList: List<Planet>)

    @Query("DELETE FROM planets")
    fun deleteAllPlanets()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPageDetails(planetList: PageDetails)

    @Query("DELETE FROM page_details")
    fun deleteAllPageDetails()
    @Query("SELECT * FROM page_details WHERE page =:page")
    fun getPageDetailsByPage(page: Int): PageDetails

    @Query("SELECT * FROM planets")
    fun getAllPlanets(): PagingSource<Int, Planet>
    @Update
    fun updatePlanets(planet: Planet)

}