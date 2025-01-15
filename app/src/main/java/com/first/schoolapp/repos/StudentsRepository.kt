package com.first.schoolapp.repos

import com.first.schoolapp.databases.TeachersDatabse
import com.first.schoolapp.entity.StudentsData
import kotlinx.coroutines.flow.Flow

class StudentsRepository(private val database: TeachersDatabse) {
    private val studentsDao = database.getStudentsDao()

    suspend fun insert(item: StudentsData) = studentsDao.insert(item)
    suspend fun delete(item: StudentsData) = studentsDao.delete(item)

    fun allStudentsData(): Flow<List<StudentsData>> = studentsDao.getAllStudents()

    suspend fun deleteByRollNo(id: Int) {
        studentsDao.deleteByRollNo(id)
    }

    suspend fun getRollNosByClass(selectedClass: String): List<String> {
        return studentsDao.getRollNosByClass(selectedClass)
    }

    suspend fun update(student: StudentsData) {
        return studentsDao.update(student)
    }
}
