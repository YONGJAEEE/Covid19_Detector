package com.example.covid19detector.model

import androidx.room.*

@Entity
data class Schedule(
    @PrimaryKey val IDX: Int,
    @ColumnInfo(name = "latLng") val latLng: String,

    @ColumnInfo(name = "date") val date: String?,

    @ColumnInfo(name = "time") val time: String?,
)