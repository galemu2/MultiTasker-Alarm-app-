package com.ctrlaccess.multitasker.database

import android.content.Context
import androidx.room.*
import com.ctrlaccess.multitasker.database.dao.AlarmDao
import com.ctrlaccess.multitasker.database.dao.MultitaskerDao
import com.ctrlaccess.multitasker.database.dao.ScheduleDao
import com.ctrlaccess.multitasker.database.entities.Alarm
import com.ctrlaccess.multitasker.database.entities.Schedule
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(
    entities = [Alarm::class, Schedule::class],
    version = 1,
    exportSchema = false
)
abstract class MultitaskerDatabase : RoomDatabase() {

    abstract fun multitaskerDao(): MultitaskerDao
    abstract fun alarmDao(): AlarmDao
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        private val NUMBER_OF_THREADS = 4
        val executor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

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