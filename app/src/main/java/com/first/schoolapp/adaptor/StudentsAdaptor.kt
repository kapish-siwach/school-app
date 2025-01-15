package com.first.schoolapp.adaptor

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.first.schoolapp.R
import com.first.schoolapp.entity.StudentsData
import com.first.schoolapp.viewmodel.AddStudentsViewModel

class StudentsAdaptor(
    private var studentsList: List<StudentsData>,
    private val viewModel: AddStudentsViewModel
) : RecyclerView.Adapter<StudentsAdaptor.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_data_view, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentsList[position]

        holder.studentName.text = student.studentName
        holder.rollNo.text = student.rollNo
        holder.className.text = student.classInput
        holder.fatherName.text = student.fatherName
        holder.id.text = student.id.toString()

        // Full details click logic
        holder.fullDetails.setOnClickListener {
            showStudentDetailsDialog(holder.itemView.context, student)
        }

        // Delete button logic
        holder.deleteButton.setOnClickListener {
            viewModel.deleteStudentById(student.id)
            Toast.makeText(holder.itemView.context, "User Deleted Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = studentsList.size

    fun updateData(newData: List<StudentsData>) {
        studentsList = newData
        notifyDataSetChanged()
    }

    private fun showStudentDetailsDialog(context: android.content.Context, student: StudentsData) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_student_details, null)
        val dialogBuilder = AlertDialog.Builder(context).setView(dialogView)

        val studentName = dialogView.findViewById<TextView>(R.id.dialogStudentName)
        val fatherName = dialogView.findViewById<TextView>(R.id.dialogFatherName)
        val motherName = dialogView.findViewById<TextView>(R.id.dialogMotherName)
        val rollNo = dialogView.findViewById<TextView>(R.id.dialogRollNo)
        val studentPhoneNo = dialogView.findViewById<TextView>(R.id.dialogStudentPhoneNo)
        val parentPhoneNo = dialogView.findViewById<TextView>(R.id.dialogParentPhoneNo)
        val age = dialogView.findViewById<TextView>(R.id.dialogAge)
        val dateOfAdmission = dialogView.findViewById<TextView>(R.id.dialogDateOfAdmission)
        val streamInput = dialogView.findViewById<TextView>(R.id.dialogStreamInput)

        val deleteButton = dialogView.findViewById<TextView>(R.id.dialogDeleteButton)

        // Set the formatted text to the TextViews
        studentName.text = "Name = ${student.studentName}"
        fatherName.text = "Father Name = ${student.fatherName}"
        motherName.text = "Mother Name = ${student.motherName}"
        rollNo.text = "Roll No = ${student.rollNo}"
        studentPhoneNo.text = "Student Phone No = ${student.studentPhoneNo}"
        parentPhoneNo.text = "Parent Phone No = ${student.parentPhoneNo}"
        age.text = "Age = ${student.age}"
        dateOfAdmission.text = "Date of Admission = ${student.dateOfAdmission}"
        streamInput.text = "Stream = ${student.streamInput}"

        val dialog = dialogBuilder.create()


        deleteButton.setOnClickListener {
            viewModel.deleteStudentById(student.id)
            dialog.dismiss()
            Toast.makeText(context, "Student Deleted Successfully", Toast.LENGTH_SHORT).show()
        }

        dialog.show()
    }


    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val studentName: TextView = view.findViewById(R.id.s_name)
        val rollNo: TextView = view.findViewById(R.id.s_roll_no)
        val className: TextView = view.findViewById(R.id.s_class)
        val fatherName: TextView = view.findViewById(R.id.s_father_name)
        val deleteButton: ImageView = view.findViewById(R.id.deleteStudent)
        val fullDetails: TextView = view.findViewById(R.id.fullDetails)
        val id: TextView = view.findViewById(R.id.s_id)
    }
}
