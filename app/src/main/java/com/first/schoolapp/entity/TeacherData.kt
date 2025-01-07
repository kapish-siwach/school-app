package com.first.schoolapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teacher_table")
data class TeacherData (

    @ColumnInfo(name = "name")
    var name: String,

    @PrimaryKey
    @ColumnInfo(name = "email")
    var email:String,

    @ColumnInfo(name = "password")
    var tpassword:String,

    @ColumnInfo(name = "phone")
    var tphone:String
    )