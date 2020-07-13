package com.ctrlaccess.multitasker.database

import android.content.Context
import androidx.room.*
import com.ctrlaccess.multitasker.database.dao.AlarmDao
import com.ctrlaccess.multitasker.database.dao.MultitaskerDao
import com.ctrlaccess.multitasker.database.dao.ScheduleDao
import com.ctrlaccess.multitasker.database.entities.Alarm
import com.ctrlaccess.multitasker.database.entities.Schedule

@Database(
    entities = arrayOf(
        Alarm::class, Schedule::class
    ), version = 1, exportSchema = false
)
abstract class MultitaskerDatabase : RoomDatabase() {

    abstract fun multitaskerDao(): MultitaskerDao
    abstract fun alarmDao(): AlarmDao
    abstract fun scheduleDao(): ScheduleDao

    companion object {

        @Volatile
        private var INSTANCE: MultitaskerDatabase? = null

        fun getDatabase(context: Context): MultitaskerDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MultitaskerDatabase::class.java,
                    "multitasker_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}