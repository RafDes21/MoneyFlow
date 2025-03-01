package com.rafdev.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.rafdev.data.model.entities.Budget

@Dao
interface BudgetDao {

    @Query("SELECT * FROM budget WHERE id=1")
    fun getBudget():Budget
}