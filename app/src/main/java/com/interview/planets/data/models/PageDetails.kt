package com.interview.planets.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("page_details")
data class PageDetails(
    @PrimaryKey(autoGenerate = false)
    var page: Int,
    var nextPage: Int?,
    var previousPage: Int?,
)