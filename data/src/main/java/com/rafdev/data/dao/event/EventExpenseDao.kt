package com.rafdev.data.dao.event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafdev.data.entities.event.EventExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventExpenseDao {

    @Query("SELECT * FROM event_expenses WHERE eventId = :eventId")
    fun getExpenses(eventId: Int): Flow<List<EventExpenseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: EventExpenseEntity)

    @Query("SELECT SUM(amount) FROM event_expenses WHERE eventId = :eventId")
    suspend fun getTotal(eventId: Int): Double?
}
