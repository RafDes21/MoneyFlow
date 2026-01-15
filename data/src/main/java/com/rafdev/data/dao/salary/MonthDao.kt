package com.rafdev.data.dao.salary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rafdev.data.entities.MonthPeriodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthDao {

    @Query("SELECT * FROM month_period ORDER BY year DESC, month DESC")
    fun getAllMonths(): Flow<List<MonthPeriodEntity>>

    @Query("SELECT * FROM month_period WHERE year = :year AND month = :month LIMIT 1")
    suspend fun getMonth(year: Int, month: Int): MonthPeriodEntity?

    @Insert
    suspend fun insertMonth(month: MonthPeriodEntity): Long
}