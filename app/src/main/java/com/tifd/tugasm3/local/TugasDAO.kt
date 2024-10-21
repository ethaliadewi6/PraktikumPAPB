package com.tifd.tugasm3.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy

@Dao
interface TugasDAO {

    @Query("SELECT * FROM tugas")
    fun getAllTugas(): LiveData<List<Tugas>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTugas(tugas: Tugas)

    @Query("UPDATE tugas SET selesai = :selesai WHERE id = :id")
    fun updateStatus(id: Int, selesai: Boolean)

}