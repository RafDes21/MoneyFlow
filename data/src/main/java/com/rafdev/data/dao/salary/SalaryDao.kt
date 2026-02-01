package com.rafdev.data.dao.salary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafdev.data.entities.SalaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SalaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SalaryEntity)

    @Query("SELECT * FROM salary")
    fun getAll(): Flow<List<SalaryEntity>>


    @Query("SELECT * FROM salary WHERE id = :id")
    suspend fun getById(id: Int): SalaryEntity?

    @Query("DELETE FROM salary WHERE id = :id")
    suspend fun deleteById(id: Int)

}