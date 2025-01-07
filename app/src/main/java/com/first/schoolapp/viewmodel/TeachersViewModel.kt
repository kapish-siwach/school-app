package com.first.schoolapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.first.schoolapp.entity.TeacherData
import com.first.schoolapp.repos.TeachersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeachersViewModel(private val repository: TeachersRepository) : ViewModel() {

    private val _teacherData = MutableLiveData<TeacherData?>()
    val teacherData: LiveData<TeacherData?> get() = _teacherData

    // Fetch teacher by email
    fun getTeacherByEmail(email: String) {
        viewModelScope.launch {
            val teacher = repository.getTeacherByEmail(email)
            _teacherData.postValue(teacher)
        }
    }

    // Insert a new teacher
    fun insert(item: TeacherData) = viewModelScope.launch {
        repository.insert(item)
    }

    // Delete a teacher
    fun delete(item: TeacherData) = viewModelScope.launch {
        repository.delete(item)
    }

    // Check if email is unique
    suspend fun isEmailUnique(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            repository.isEmailExists(email) == 0
        }
    }

    // Validate teacher credentials
    suspend fun validateTeacher(email: String, password: String): TeacherData? {
        return withContext(Dispatchers.IO) {
            repository.validateTeacher(email, password)
        }
    }

    // Fetch all teacher data
    fun allTeacherData() = repository.allTeacherData()
}

class TeachersViewModelFactory(private val repository: TeachersRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeachersViewModel::class.java)) {
            return TeachersViewModel(repository) as T
            } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
