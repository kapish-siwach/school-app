package com.first.schoolapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.first.schoolapp.adaptor.StudentsAdaptor
import com.first.schoolapp.databinding.FragmentStudentsBinding
import com.first.schoolapp.viewmodel.AddStudentsViewModel
import com.first.schoolapp.viewmodel.AddStudentsViewModelFactory
import com.first.schoolapp.repos.StudentsRepository
import com.first.schoolapp.databases.StudentsDatabase

class StudentsFragment : Fragment() {

    private lateinit var binding: FragmentStudentsBinding
    private lateinit var studentsAdaptor: StudentsAdaptor
    private lateinit var addStudentsViewModel: AddStudentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel with a Factory
        val database = StudentsDatabase(requireContext())
        val repository = StudentsRepository(database)
        val factory = AddStudentsViewModelFactory(repository)
        addStudentsViewModel = ViewModelProvider(this, factory).get(AddStudentsViewModel::class.java)

        // Initialize RecyclerView with ViewModel
        studentsAdaptor = StudentsAdaptor(emptyList(), addStudentsViewModel)
        binding.studentsRecycler.apply {
            adapter = studentsAdaptor
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Observe studentsData LiveData
        addStudentsViewModel.studentsData.observe(viewLifecycleOwner) { students ->
            studentsAdaptor.updateData(students)
        }

        // Fetch data from the database
        addStudentsViewModel.fetchStudentsData()
    }
}
