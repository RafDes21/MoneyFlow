package com.rafdev.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.rafdev.data.model.entities.ExpenseEntity

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): List<ExpenseEntity>
}