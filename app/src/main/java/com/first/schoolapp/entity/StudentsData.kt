package com.first.schoolapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students_table")
data class StudentsData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "student_name")
    val studentName: String="",

    @ColumnInfo(name = "father_name")
    val fatherName: String="",
    @ColumnInfo(name = "mother_name")
    val motherName: String="",
    @ColumnInfo(name = "student_phone_no")
    val studentPhoneNo: String="",
    @ColumnInfo(name = "parent_phone_no")
    val parentPhoneNo: String="",
    @ColumnInfo(name = "age")
    val age: String="",

    @ColumnInfo(name = "roll_no")
    val rollNo: String="",
    @ColumnInfo(name = "date_of_admission")
    val dateOfAdmission: String="",
    @ColumnInfo(name = "class_input")
    val classInput: String="",
    @ColumnInfo(name = "stream_input")
    val streamInput: String="Not Applicable"
)
