package com.example.covid19detector.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.covid19detector.model.Schedule
import java.util.*

@Dao
interface ScheduleDAO {
    @Insert
    fun insertSchedule(vararg schedule: Schedule)

    @Query("SELECT * FROM schedule WHERE date = :selectedDate ")
    fun getData(selectedDate: Date): List<Schedule>
}