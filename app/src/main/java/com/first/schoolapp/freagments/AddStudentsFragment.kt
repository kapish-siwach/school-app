package com.first.schoolapp.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.first.schoolapp.R
import com.first.schoolapp.databases.StudentsDatabase
import com.first.schoolapp.databinding.FragmentAddStudentsBinding
import com.first.schoolapp.entity.StudentsData
import com.first.schoolapp.repos.StudentsRepository
import com.first.schoolapp.viewmodel.AddStudentsViewModel
import com.first.schoolapp.viewmodel.AddStudentsViewModelFactory
import kotlinx.coroutines.launch
import java.util.*

class AddStudentsFragment : Fragment() {

    private lateinit var binding: FragmentAddStudentsBinding
    private val repository: StudentsRepository by lazy {
        StudentsRepository(StudentsDatabase.invoke(requireContext()))  // Pass context here
    }

    private val viewModel: AddStudentsViewModel by viewModels {
        val database = StudentsDatabase.invoke(requireContext())  // Pass context here as well
        val repository = StudentsRepository(database)
        AddStudentsViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateofAdmission: TextView = binding.dateOfAdmission
        val classInput = binding.classInput
        val streamInput = binding.streamInput
        val submitButton = binding.submitButton

        // Load class array into Spinner
        val classAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.clazz,
            android.R.layout.simple_spinner_item
        )
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.classInput.adapter = classAdapter

        // Load stream array into Spinner
        val streamAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.stream,
            android.R.layout.simple_spinner_item
        )
        streamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.streamInput.adapter = streamAdapter

        // Initially hide stream input if it's not 11th or 12th
        streamInput.visibility = if (classInput.selectedItem.toString() == "11th" || classInput.selectedItem.toString() == "12th") {
            View.VISIBLE
        } else {
            View.GONE
        }

        // Show or hide stream input based on selected class
        classInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                val selectedItem = parentView?.getItemAtPosition(position).toString()

                streamInput.visibility = if (selectedItem == "11th" || selectedItem == "12th") {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(), "Please select an Option", Toast.LENGTH_SHORT).show()
            }
        }

        // Date picker functionality
        dateofAdmission.setOnClickListener {
            showDatePicker()
        }

        // Observe selected date from ViewModel
        viewModel.selectedDate.observe(viewLifecycleOwner, Observer { date ->
            dateofAdmission.setText(date)
        })

        // Submit button logic
        submitButton.setOnClickListener {
            val studentName = binding.fullName.text.toString().trim()
            val fatherName = binding.fatherName.text.toString().trim()
            val motherName = binding.motherName.text.toString().trim()
            val studentPhoneNo = binding.studentPhoneNo.text.toString().trim()
            val parentPhoneNo = binding.parentPhoneNo.text.toString().trim()
            val age = binding.age.text.toString().trim()
            val rollNo = binding.rollNo.text.toString().trim()

            val selectedClass = binding.classInput.selectedItem?.toString() ?: "Select Class"
            val selectedStream = binding.streamInput.selectedItem?.toString() ?: "Select Stream"

            var isValid = true

            // Reset error messages
            binding.fullName.error = null
            binding.fatherName.error = null
            binding.motherName.error = null
            binding.studentPhoneNo.error = null
            binding.parentPhoneNo.error = null
            binding.age.error = null
            binding.rollNo.error = null

            // Validation for fields
            if (studentName.isEmpty()) {
                binding.fullName.error = "Student Name cannot be empty"
                isValid = false
            }
            if (fatherName.isEmpty()) {
                binding.fatherName.error = "Father's Name cannot be empty"
                isValid = false
            }
            if (motherName.isEmpty()) {
                binding.motherName.error = "Mother's Name cannot be empty"
                isValid = false
            }
            if (studentPhoneNo.isEmpty()) {
                binding.studentPhoneNo.error = "Student Phone Number cannot be empty"
                isValid = false
            }
            if (parentPhoneNo.isEmpty()) {
                binding.parentPhoneNo.error = "Parent Phone Number cannot be empty"
                isValid = false
            }
            if (age.isEmpty()) {
                binding.age.error = "Age cannot be empty"
                isValid = false
            }
            if (rollNo.isEmpty()) {
                binding.rollNo.error = "Roll Number cannot be empty"
                isValid = false
            }

            if (selectedClass == "Select Class") {
                Toast.makeText(requireContext(), "Please select a valid class", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            if (streamInput.visibility == View.VISIBLE && selectedStream == "Select Stream") {
                Toast.makeText(requireContext(), "Please select a valid stream", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            if (dateofAdmission.text.toString().trim().isEmpty()) {
                Toast.makeText(requireContext(), "Date of Admission cannot be empty", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            // Proceed if all fields are valid
            if (isValid) {
                val student = StudentsData(
                    studentName = studentName,
                    fatherName = fatherName,
                    motherName = motherName,
                    studentPhoneNo = studentPhoneNo,
                    parentPhoneNo = parentPhoneNo,
                    age = age,
                    rollNo = rollNo,
                    dateOfAdmission = dateofAdmission.text.toString(),
                    classInput = selectedClass,
                    streamInput = selectedStream
                )

                lifecycleScope.launch {
                    repository.insert(student)
                    onFormSubmitted()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addAnother.setOnClickListener {
            // Reset all fields to default values
           resetAllFields()
        }

        binding.showAll.setOnClickListener {
            resetAllFields()
            val studentsFragment = StudentsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, studentsFragment) // Replace 'fragment_container' with your container's ID
                .addToBackStack(null) // Optional, adds the transaction to the back stack
                .commit()
        }
    }

    private fun resetAllFields() {

        binding.fullName.text.clear()
        binding.fatherName.text.clear()
        binding.motherName.text.clear()
        binding.studentPhoneNo.text.clear()
        binding.parentPhoneNo.text.clear()
        binding.age.text.clear()
        binding.rollNo.text.clear()
        binding.dateOfAdmission.text = "" // Reset the date

        binding.classInput.setSelection(0) // Reset to the default "Select Class"
        binding.streamInput.setSelection(0) // Reset to the default "Select Stream"
        binding.streamInput.visibility = View.GONE // Hide stream input unless needed

        binding.submitButton.visibility = View.VISIBLE
        binding.showGoBtn.visibility = View.GONE
    }

    private fun onFormSubmitted() {
        binding.submitButton.visibility = View.GONE
        binding.showGoBtn.visibility = View.VISIBLE
        Toast.makeText(requireContext(), "Data Successfully Submitted", Toast.LENGTH_SHORT).show()
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                viewModel.setDate(year, month, dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }
}
