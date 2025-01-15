package com.first.schoolapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "update_table")
data class UpdateData(

    @PrimaryKey
    val id:String= UUID.randomUUID().toString(),
    val update:String
)
