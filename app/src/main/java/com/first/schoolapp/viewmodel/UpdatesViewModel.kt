package com.first.schoolapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.first.schoolapp.databases.TeachersDatabse
import com.first.schoolapp.entity.UpdateData
import kotlinx.coroutines.launch

class UpdatesViewModel(application: Application) : AndroidViewModel(application) {

    private val updateDao = TeachersDatabse.invoke(application).getUpdateDao()
    val allUpdates: LiveData<List<UpdateData>> = updateDao.getAllUpdates()

    fun addUpdate(update: UpdateData) {
        viewModelScope.launch {
            updateDao.insertUpdate(update)
        }
    }

    fun deleteUpdate(id: String) {
        viewModelScope.launch {
            updateDao.deleteUpdate(id)
        }
    }
}
