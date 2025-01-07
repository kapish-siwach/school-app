package com.first.schoolapp.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.first.schoolapp.dao.StudentsDao
import com.first.schoolapp.entity.StudentsData

@Database(entities = [StudentsData::class], version = 2, exportSchema = false)
abstract class StudentsDatabase : RoomDatabase() {

    abstract fun getStudentsDao(): StudentsDao

    companion object {
        @Volatile
        private var instance: StudentsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): StudentsDatabase =
            instance ?: synchronized(LOCK) {
                instance ?: createDatabase(context).also { instance = it }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                StudentsDatabase::class.java, "Students.db")
                .fallbackToDestructiveMigration() // Allows dropping the table and recreating it
                .build()
    }
}


