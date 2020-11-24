package com.example.covid19detector.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.*

@Entity
data class Schedule(
    @PrimaryKey val IDX: Int,
    @ColumnInfo(name = "place") val place: String?,
    @ColumnInfo(name = "date") val date: Date?,
    @ColumnInfo(name = "time") val time: Time?,
    )