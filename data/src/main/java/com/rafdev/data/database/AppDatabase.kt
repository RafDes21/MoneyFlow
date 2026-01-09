package com.rafdev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafdev.data.dao.BudgetDao
import com.rafdev.data.dao.CreditCardDao
import com.rafdev.data.dao.ExpenseDao
import com.rafdev.data.dao.event.EventDao
import com.rafdev.data.dao.event.EventExpenseDao
import com.rafdev.data.entities.BudgetEntity
import com.rafdev.data.entities.CreditCardEntity
import com.rafdev.data.entities.ExpenseEntity
import com.rafdev.data.entities.event.EventEntity
import com.rafdev.data.entities.event.EventExpenseEntity

@Database(
    entities = [
        BudgetEntity::class,
        ExpenseEntity::class,
        CreditCardEntity::class,
        EventEntity::class,
        EventExpenseEntity::class
    ],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun budgetDao(): BudgetDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun creditCardDao(): CreditCardDao

    abstract fun eventDao(): EventDao
    abstract fun eventExpenseDao(): EventExpenseDao
}