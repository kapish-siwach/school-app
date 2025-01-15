package com.first.schoolapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.first.schoolapp.R
import com.first.schoolapp.entity.Services
import com.first.schoolapp.freagments.AddStudentsFragment
import com.first.schoolapp.fragments.StudentsFragment
import com.first.schoolapp.fragments.UpdateFragment
import com.first.schoolapp.freagments.AssignmentFragment
import com.first.schoolapp.freagments.AttendanceFragment
import com.first.schoolapp.freagments.BatchesFragment
import com.first.schoolapp.freagments.EnquiryManagerFragment
import com.first.schoolapp.freagments.FeesFragment
import com.first.schoolapp.freagments.StudentPerformanceFragment


class ItemsViewModel(application: Application): AndroidViewModel(application) {
    private val _items = MutableLiveData<List<Services>>()
    val items: LiveData<List<Services>> get() = _items

    private val _navigateToActivity = MutableLiveData<Class<out androidx.fragment.app.Fragment>?>()
    val navigateTo: LiveData<Class<out androidx.fragment.app.Fragment>?> get() = _navigateToActivity


    init {
        loadItems()

    }

    private fun loadItems() {

        _items.value = listOf(

            Services("Batches", R.drawable.baseline_class_24),
            Services("Students", R.drawable.baseline_boy_24),
            Services("Add Students", R.drawable.baseline_person_add_alt_24),
            Services("Attendance", R.drawable.baseline_attribution_24),
            Services("Fees", R.drawable.baseline_attach_money_24),
            Services("Student Performance", R.drawable.baseline_area_chart_24),
            Services("Enquiry Manager", R.drawable.baseline_help_24),
            Services("Assignment", R.drawable.baseline_assignment_24),
            Services("TODO",R.drawable.baseline_note_add_24)
        )
    }

    fun onServiceClicked(service: Services) {
        _navigateToActivity.value = when (service.title) {
            "Add Students" -> AddStudentsFragment::class.java
            "Batches" -> BatchesFragment::class.java
            "Students" -> StudentsFragment::class.java
            "Attendance" -> AttendanceFragment::class.java
            "Fees" -> FeesFragment::class.java
            "Student Performance" -> StudentPerformanceFragment::class.java
            "Enquiry Manager" -> EnquiryManagerFragment::class.java
            "Assignment"-> AssignmentFragment::class.java
            "TODO"-> UpdateFragment::class.java
            else -> null
        }
        _navigateToActivity.value=null
    }

}
class ItemsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsViewModel::class.java)) {
            return ItemsViewModel(application) as T
        }else{
        throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
