package com.example.covid19detector.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.covid19detector.model.Schedule

@Database(entities = [Schedule::class], version = 1)
abstract class ScheduleDatabase: RoomDatabase() {
    abstract fun scheduleDAO(): ScheduleDAO

    companion object {
        private var INSTANCE: ScheduleDatabase? = null

        fun getInstance(context: Context): ScheduleDatabase? {
            if (INSTANCE == null) {
                synchronized(Database::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ScheduleDatabase::class.java, "schedule.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}