package com.first.schoolapp.repos

import com.first.schoolapp.databases.TeachersDatabse
import com.first.schoolapp.entity.TeacherData

class TeachersRepository(private val db: TeachersDatabse)  {

    suspend fun insert(item: TeacherData) = db.getTeachersDao().insert(item)
    suspend fun delete(item: TeacherData) = db.getTeachersDao().delete(item)
    suspend fun isEmailExists(email: String) = db.getTeachersDao().isEmailExists(email)

    fun allTeacherData()=db.getTeachersDao().getAllTeachers()
    suspend fun validateTeacher(email: String, password: String):TeacherData?{
        return db.getTeachersDao().validateTeacher(email, password)
    }
    suspend fun getTeacherByEmail(email: String):TeacherData?{
        return db.getTeachersDao().getTeacherByEmail(email)
    }
}