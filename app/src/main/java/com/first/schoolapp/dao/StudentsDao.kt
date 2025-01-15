package com.first.schoolapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.first.schoolapp.entity.StudentsData
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(studentsData: StudentsData)

    @Query("SELECT * FROM students_table")
    fun getAllStudents(): Flow<List<StudentsData>>


    @Query("DELETE FROM students_table WHERE id = :id")
    suspend fun deleteByRollNo(id: kotlin.Int)

    @Delete
    suspend fun delete(studentsData: StudentsData)

    @Query("SELECT * FROM students_table WHERE roll_no = :rollNo")
    suspend fun getStudentByRollNo(rollNo: String): StudentsData?

    @Query("SELECT roll_no FROM students_table WHERE class_input = :selectedClass")
    suspend fun getRollNosByClass(selectedClass: String): List<String>

    @Update
    suspend fun update(student: StudentsData)

}