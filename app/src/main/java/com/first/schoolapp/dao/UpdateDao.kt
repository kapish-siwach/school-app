package com.first.schoolapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.first.schoolapp.entity.UpdateData

@Dao
interface UpdateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdate(update: UpdateData)

    @Query("SELECT * FROM update_table")
    fun getAllUpdates(): LiveData<List<UpdateData>>


    @Query("DELETE FROM update_table WHERE id = :id")
    suspend fun deleteUpdate(id: String)

}