package com.first.schoolapp.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.first.schoolapp.dao.StudentsDao
import com.first.schoolapp.dao.TeachersDao
import com.first.schoolapp.dao.UpdateDao
import com.first.schoolapp.entity.StudentsData
import com.first.schoolapp.entity.TeacherData
import com.first.schoolapp.entity.UpdateData

@Database(entities = [TeacherData::class,UpdateData::class,StudentsData::class], version = 4, exportSchema = false)
abstract class TeachersDatabse:RoomDatabase() {
    abstract fun getTeachersDao(): TeachersDao
    abstract fun getUpdateDao(): UpdateDao
    abstract fun getStudentsDao(): StudentsDao


    companion object{
        private var instance:TeachersDatabse?=null
        private val LOCK=Any()

        operator fun invoke(context: Context)= instance?: synchronized(LOCK){
            instance?:createDatabse(context).also{
                instance = it
            }
        }
        private fun createDatabse(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                TeachersDatabse::class.java,
                "Teachers.db")
                .fallbackToDestructiveMigration()
                .build()
    }

}