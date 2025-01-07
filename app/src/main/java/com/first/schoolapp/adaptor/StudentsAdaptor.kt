package com.first.schoolapp.adaptor

import android.annotation.SuppressLint
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
        holder.id.text = student.id
        // Delete button logic
        holder.deleteButton.setOnClickListener {
            val id = student.id
            if (id.isNotEmpty()) {
                viewModel.deleteStudentById(id)

                Toast.makeText(holder.itemView.context, "User Deleted Successfully", Toast.LENGTH_SHORT).show()
                studentsList = studentsList.filter { it.id != id }
                notifyItemRemoved(position)
            }
        }
    }

    override fun getItemCount(): Int = studentsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<StudentsData>) {
        studentsList = newData
        notifyDataSetChanged()
    }

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val studentName: TextView = view.findViewById(R.id.s_name)
        val rollNo: TextView = view.findViewById(R.id.s_roll_no)
        val className: TextView = view.findViewById(R.id.s_class)
        val fatherName: TextView = view.findViewById(R.id.s_father_name)
        val deleteButton: ImageView = view.findViewById(R.id.deleteStudent)
        val id: TextView = view.findViewById(R.id.s_id)
    }
}
