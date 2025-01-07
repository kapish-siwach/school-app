package com.first.schoolapp.dao

import androidx.room.*
import com.first.schoolapp.entity.TeacherData


@Dao
interface TeachersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(teacherData: TeacherData)

    @Delete
    suspend fun delete(teacherData: TeacherData)

    @Query("Select * from teacher_table")
    fun getAllTeachers(): List<TeacherData>

    @Query("SELECT COUNT(*) FROM teacher_table WHERE email = :email")
    suspend fun isEmailExists(email: String): Int

    @Query("SELECT * FROM teacher_table WHERE email = :email AND password = :password")
    suspend fun validateTeacher(email: String, password: String): TeacherData?

    @Query("SELECT * FROM teacher_table WHERE email = :email LIMIT 1")
    suspend fun getTeacherByEmail(email: String): TeacherData?

}