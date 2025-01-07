package com.first.schoolapp.repos

import com.first.schoolapp.databases.StudentsDatabase
import com.first.schoolapp.entity.StudentsData
import kotlinx.coroutines.flow.Flow

class StudentsRepository(private val database: StudentsDatabase) {
    private val studentsDao = database.getStudentsDao()

    suspend fun insert(item: StudentsData) = studentsDao.insert(item)
    suspend fun delete(item: StudentsData) = studentsDao.delete(item)

    fun allStudentsData(): Flow<List<StudentsData>> = studentsDao.getAllStudents()
    suspend fun deleteByRollNo(id: String) {
        studentsDao.deleteByRollNo(id)
    }
}

