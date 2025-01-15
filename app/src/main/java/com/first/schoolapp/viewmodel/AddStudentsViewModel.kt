package com.first.schoolapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.first.schoolapp.entity.StudentsData
import com.first.schoolapp.repos.StudentsRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddStudentsViewModel(private val repository: StudentsRepository) : ViewModel() {

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    private val _studentsData = MutableLiveData<List<StudentsData>>()
    val studentsData: LiveData<List<StudentsData>> get() = _studentsData

    private val _selectedStudent = MutableLiveData<StudentsData?>()
    val selectedStudent: LiveData<StudentsData?> get() = _selectedStudent

    init {
        fetchStudentsData()
    }

    fun fetchStudentsData() {
        viewModelScope.launch {
            repository.allStudentsData().collect { data ->
                _studentsData.postValue(data)
            }
        }
    }

    suspend fun insert(item: StudentsData) = repository.insert(item)

    suspend fun delete(item: StudentsData) = repository.delete(item)

    fun deleteStudentById(id: Int) {
        viewModelScope.launch {
            repository.deleteByRollNo(id)
        }
    }


    fun setSelectedStudent(student: StudentsData) {
        _selectedStudent.value = student
    }

    fun updateStudentDetails(student: StudentsData) {
        viewModelScope.launch {
            repository.update(student)
        }
    }

    fun setDate(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        _selectedDate.value = formatter.format(calendar.time)
    }


}

class AddStudentsViewModelFactory(private val repository: StudentsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddStudentsViewModel::class.java)) {
            return AddStudentsViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
